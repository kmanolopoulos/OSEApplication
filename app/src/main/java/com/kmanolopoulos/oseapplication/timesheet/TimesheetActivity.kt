package com.kmanolopoulos.oseapplication.timesheet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmanolopoulos.oseapplication.databases.DataFileBrowser
import com.kmanolopoulos.oseapplication.R
import kotlinx.android.synthetic.main.activity_timesheet.*

class TimesheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val stationFrom = intent.getStringExtra("TimesheetActivity.SEARCH_FROM") ?: ""
        val stationTo = intent.getStringExtra("TimesheetActivity.SEARCH_TO") ?: ""
        val date = intent.getStringExtra("TimesheetActivity.SEARCH_DATE") ?: ""
        val timesheetEntries: ArrayList<Any> = ArrayList()

        timesheetEntries.add(TimesheetHeaderEntry())
        timesheetEntries.addAll(
            DataFileBrowser(
                this
            ).getTimesheets(stationFrom, stationTo, date))

        rec_search_layout.layoutManager = LinearLayoutManager(this)
        rec_search_layout.adapter =
            TimesheetAdapter(
                timesheetEntries
            )
    }
}
