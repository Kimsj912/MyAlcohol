package com.example.lastproj_mp.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lastproj_mp.*

@Database(entities = [History::class, Alcohol::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun alcoholDao(): AlcoholDao
}