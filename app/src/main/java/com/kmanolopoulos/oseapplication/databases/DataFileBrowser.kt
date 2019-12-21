package com.kmanolopoulos.oseapplication.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kmanolopoulos.oseapplication.timesheet.TimesheetDataEntry


class DataFileBrowser(val context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {

    // Called when database is created
    override fun onCreate(dbase: SQLiteDatabase?) {}

    // Called when database is updated through versions
    override fun onUpgrade(dbase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    // TODO: Implement this method based on real data
    fun getAllStations(): List<String> {
        return listOf("Athens", "Sfendali", "Acharnai")
    }

    // TODO: Implement this method based on real data
    fun getTimesheets(
        stationFrom: String,
        stationTo: String,
        date: String
    ): List<TimesheetDataEntry> {
        var entries = ArrayList<TimesheetDataEntry>()

        entries.clear()
        entries.add(
            TimesheetDataEntry(
                stationFrom,
                stationTo,
                "11:52",
                "16:28"
            )
        )
        entries.add(
            TimesheetDataEntry(
                stationFrom,
                stationTo,
                "12:52",
                "13:35"
            )
        )
        entries.add(
            TimesheetDataEntry(
                stationFrom,
                stationTo,
                "13:52",
                "17:28"
            )
        )
        entries.add(
            TimesheetDataEntry(
                stationFrom,
                stationTo,
                "14:52",
                "18:28"
            )
        )
        entries.add(
            TimesheetDataEntry(
                stationFrom,
                stationTo,
                "15:52",
                "19:28"
            )
        )

        return entries
    }

    // Constants used for calling base class constructor
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "timesheetsDB"
    }
}

