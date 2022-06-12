package com.example.lastproj_mp

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history")
    fun getAll(): List<History>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg history: History)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(history: History)

    @Delete
    fun delete(history: History)

    @Query("DELETE FROM history WHERE hid = :hid")
    fun deleteById(hid: Int): Int
}

@Dao
interface AlcoholDao {
    @Query("SELECT * FROM alcohol")
    fun getAll(): List<Alcohol>

    @Query("SELECT alcName FROM alcohol")
    fun getNameAll(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alcohol: Alcohol)

    @Delete
    fun delete(alcohol: Alcohol)

    @Query("SELECT percent FROM alcohol WHERE alcName LIKE :sAlcName")
    abstract fun getPercentFromAlcType(sAlcName: String): Byte

    @Query("DELETE FROM alcohol WHERE alcName = :alcName")
    fun deleteByName(alcName: String): Int

}