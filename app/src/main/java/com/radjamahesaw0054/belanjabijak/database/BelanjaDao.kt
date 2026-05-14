package com.radjamahesaw0054.belanjabijak.database


import androidx.room.*
import com.radjamahesaw0054.belanjabijak.model.KeranjangBelanja
import com.radjamahesaw0054.belanjabijak.model.Pengeluaran
import kotlinx.coroutines.flow.Flow

@Dao
interface BelanjaDao {
    @Insert
    suspend fun insertPengeluaran(pengeluaran: Pengeluaran)

    @Update
    suspend fun updatePengeluaran(pengeluaran: Pengeluaran)

    @Delete
    suspend fun deletePengeluaran(pengeluaran: Pengeluaran)

    @Query("SELECT * FROM pengeluaran WHERE tanggal = :bulanTahun ORDER BY id DESC")
    fun getPengeluaranBerdasarkanBulan(bulanTahun: String): Flow<List<Pengeluaran>>

    @Insert
    suspend fun insertKeranjang(item: KeranjangBelanja)

    @Query("SELECT * FROM keranjang_belanja ORDER BY id DESC")
    fun getAllKeranjang(): Flow<List<KeranjangBelanja>>

    @Delete
    suspend fun deleteKeranjang(item: KeranjangBelanja)
}