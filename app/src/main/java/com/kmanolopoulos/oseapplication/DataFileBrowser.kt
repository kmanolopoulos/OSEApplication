package com.kmanolopoulos.oseapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.StandardCharsets


class DataFileBrowser(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Called when database is created
    override fun onCreate(dbase: SQLiteDatabase?) {}

    // Called when database is updated through versions
    override fun onUpgrade(dbase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    // Parse JSON file data to rebuild database data
    fun parseJsonData(jsonFile: File) {}

    // Constants used for calling base class constructor
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "timesheetsDB"
    }
}

