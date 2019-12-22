package com.kmanolopoulos.oseapplication.timesheet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmanolopoulos.oseapplication.R
import com.kmanolopoulos.oseapplication.models.TimesheetsModel
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

        val query = TimesheetQuery(stationFrom, stationTo, date)

        TimesheetExecuteQuery(this).startDownload(query)
    }

    fun onResultsFound(timesheet: List<TimesheetsModel>) {
        val timesheetEntries: ArrayList<Any> = ArrayList()

        timesheetEntries.add(TimesheetHeaderEntry())

        timesheet.forEach {
            timesheetEntries.add(
                TimesheetDataEntry(
                    it.from,
                    it.to,
                    it.timeFrom,
                    it.timeTo
                )
            )
        }

        rec_search_layout.layoutManager = LinearLayoutManager(this)
        rec_search_layout.adapter = TimesheetAdapter(timesheetEntries)
    }
}
