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

    @Query("SELECT * FROM pengeluaran WHERE tanggal LIKE :bulan || '%' AND isDeleted = 0 ORDER BY id DESC")
    fun getPengeluaranBerdasarkanBulan(bulan: String): Flow<List<Pengeluaran>>

    @Insert
    suspend fun insertKeranjang(item: KeranjangBelanja)

    @Query("SELECT * FROM keranjang_belanja ORDER BY id DESC")
    fun getAllKeranjang(): Flow<List<KeranjangBelanja>>

    @Delete
    suspend fun deleteKeranjang(item: KeranjangBelanja)

    @Query("SELECT * FROM pengeluaran WHERE isDeleted = 1 ORDER BY id DESC")
    fun getRecycleBin(): Flow<List<Pengeluaran>>

    @Delete
    suspend fun deletePermanen(pengeluaran: Pengeluaran)
}