package com.example.aio_bazar.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aio_bazar.model.data.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase():RoomDatabase() {
    abstract fun productDao():ProductDao
}