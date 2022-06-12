package com.example.lastproj_mp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lastproj_mp.*

@Database(entities = [History::class, Alcohol::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun alcoholDao(): AlcoholDao
}