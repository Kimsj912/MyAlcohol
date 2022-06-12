package com.example.lastproj_mp

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAll(): List<History>

    @Insert
    fun insertAll(vararg history: History)

    @Insert
    fun insert(history: History)

    @Delete
    fun delete(history: History)

}

@Dao
interface AlcoholDao {
    @Query("SELECT * FROM alcohol")
    fun getAll(): List<Alcohol>

    @Insert
    fun insertAll(vararg alcohol: Alcohol)

    @Delete
    fun delete(alcohol: Alcohol)

    @Query("SELECT percent FROM alcohol WHERE alcName LIKE :sAlcName")
    abstract fun getPercentFromAlcType(sAlcName: String): Byte

}