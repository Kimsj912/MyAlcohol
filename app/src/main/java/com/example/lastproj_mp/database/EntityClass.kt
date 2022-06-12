package com.example.lastproj_mp

import androidx.room.*

@Entity
data class History(
    @PrimaryKey(autoGenerate = true) val hid: Int =0,
    @ColumnInfo val date: String,
    @ColumnInfo val alcName: String,
    @ColumnInfo val drunk: Int,
    @ColumnInfo val alcAmount: Double,
)

@Entity
data class Alcohol(
    @PrimaryKey val alcName: String,
    @ColumnInfo val alcType: String,
    @ColumnInfo val bottle: Int,
    @ColumnInfo val cup: Int,
    @ColumnInfo val percent: Double,
)
