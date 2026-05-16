package com.radjamahesaw0054.belanjabijak.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radjamahesaw0054.belanjabijak.database.BelanjaDao
import com.radjamahesaw0054.belanjabijak.model.Pengeluaran
import com.radjamahesaw0054.belanjabijak.ui.util.SettingsPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(
    private val dao: BelanjaDao,
    private val preferences: SettingsPreferences
) : ViewModel() {

    private val currentMonth: String
        get() = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

    val filterBulan: StateFlow<String> = preferences.getBulanFilter.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = currentMonth
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val listPengeluaran: StateFlow<List<Pengeluaran>> = filterBulan.flatMapLatest { bulan ->
        dao.getPengeluaranBerdasarkanBulan(bulan)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    fun tambahPengeluaran(nama: String, harga: Double, jumlah: Int, kategori: String, tanggal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val baru = Pengeluaran(
                namaBarang = nama,
                harga = harga,
                jumlah = jumlah,
                kategori = kategori,
                tanggal = tanggal
            )
            dao.insertPengeluaran(baru)
        }
    }

    fun ubahPengeluaran(id: Long, nama: String, harga: Double, jumlah: Int, kategori: String, tanggal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updateData = Pengeluaran(
                id = id,
                namaBarang = nama,
                harga = harga,
                jumlah = jumlah,
                kategori = kategori,
                tanggal = tanggal
            )
            dao.updatePengeluaran(updateData)
        }
    }

    fun hapusPengeluaran(pengeluaran: Pengeluaran) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deletePengeluaran(pengeluaran)
        }
    }

    fun simpanFilterBulan(bulan: String) {
        viewModelScope.launch(Dispatchers.Main) {
            preferences.saveBulanFilter(bulan)
        }
    }

    val isGrid: StateFlow<Boolean> = preferences.getLayoutStatus.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    fun simpanLayoutStatus(isGrid: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            preferences.saveLayoutStatus(isGrid)
        }
    }


    val listRecycleBin: StateFlow<List<Pengeluaran>> = dao.getRecycleBin().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun pindahkanKeRecycleBin(pengeluaran: Pengeluaran) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updatePengeluaran(pengeluaran.copy(isDeleted = true))
        }
    }

    fun pulihkanDariRecycleBin(pengeluaran: Pengeluaran) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updatePengeluaran(pengeluaran.copy(isDeleted = false))
        }
    }

    fun hapusPermanenPengeluaran(pengeluaran: Pengeluaran) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deletePermanen(pengeluaran)
        }
    }
}