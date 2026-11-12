package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookingEntity::class], version = 1, exportSchema = false)
abstract class MozzoDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var INSTANCE: MozzoDatabase? = null

        fun getInstance(context: Context): MozzoDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MozzoDatabase::class.java,
                    "mozzo_studio_secure.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
