package com.radjamahesaw0054.belanjabijak.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.radjamahesaw0054.belanjabijak.model.KeranjangBelanja
import com.radjamahesaw0054.belanjabijak.model.Pengeluaran

@Database(entities = [Pengeluaran::class, KeranjangBelanja::class], version = 1, exportSchema = false)
abstract class BelanjaDb : RoomDatabase() {
    abstract val dao: BelanjaDao

    companion object {
        @Volatile
        private var INSTANCE: BelanjaDb? = null

        fun getInstance(context: Context): BelanjaDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BelanjaDb::class.java,
                    "belanja_bijak.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}