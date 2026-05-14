package com.radjamahesaw0054.belanjabijak.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keranjang_belanja")
data class KeranjangBelanja(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val namaProduk: String,
    val perkiraanHarga: Double,
    val ukuranIsi: Double,
    val isChecked: Boolean = false
)