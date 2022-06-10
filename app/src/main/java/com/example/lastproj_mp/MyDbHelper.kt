package com.example.lastproj_mp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class MyDbHelper {
    class MyDbHelper(context:Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
        object MyEntry : BaseColumns {
            const val TABLE_NAME = "Alcohol"
            const val alcType = "alcType"
            const val alcName = "alcName"
            const val bottle = "bottle"
            const val cup = "cup"
            const val percent = "percent"
        }
        val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${MyEntry.TABLE_NAME} ("+
                    "${MyEntry.alcType} TEXT ,"+
                    "${MyEntry.alcName} TEXT PRIMARY KEY,"+
                    "${MyEntry.bottle} TEXT,"+
                    "${MyEntry.cup} TEXT,"+
                    "${MyEntry.percent} TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${MyEntry.TABLE_NAME}"
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            var db = readableDatabase
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }
        companion object {
            // if you change the database schema, you must increase old version.
            const val DATABASE_VERSION = 1
            const val DATABASE_NAME = "Alcohol.db"
        }

        fun selectAll(): MutableList<MyElement> {
            val readList = mutableListOf<MyElement>()
            val db = readableDatabase
            val cursor = db.rawQuery(
                "SELECT * FROM " + MyDbHelper.MyEntry.TABLE_NAME + ";",
                null
            )
            with(cursor) {
                while (moveToNext()) {
                    readList.add(
                        MyElement(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getDouble(4)
                        )
                    )
                }
                cursor.close()
                db.close()
                return readList
            }
        }
    }
}