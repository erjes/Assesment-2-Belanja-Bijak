package com.radjamahesaw0054.belanjabijak.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengeluaran")
data class Pengeluaran(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaBarang: String,
    val harga: Double,
    val jumlah: Int,
    val tanggal: String, // Format simpan: YYYY-MM
    val kategori: String // PRIMER, SEKUNDER, TERSIER
)