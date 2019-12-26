package com.kmanolopoulos.oseapplication.timesheet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmanolopoulos.oseapplication.R
import com.kmanolopoulos.oseapplication.models.StationsModel
import com.kmanolopoulos.oseapplication.models.TimesheetQueryModel
import com.kmanolopoulos.oseapplication.models.TimesheetsModel
import kotlinx.android.synthetic.main.activity_timesheet.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TimesheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val stationsMap =
            intent.getSerializableExtra("TimesheetActivity.STATIONS_DATA") as HashMap<String, StationsModel>
        val stationFrom = intent.getStringExtra("TimesheetActivity.SEARCH_FROM") ?: ""
        val stationTo = intent.getStringExtra("TimesheetActivity.SEARCH_TO") ?: ""
        val date = intent.getStringExtra("TimesheetActivity.SEARCH_DATE") ?: ""

        val query = TimesheetQueryModel(
            stationsMap,
            stationFrom,
            stationTo,
            date
        )
        setProgressBarStatus(true)
        TimesheetExecuteQuery(this, query).startDownload()
    }

    fun onResultsFound(timesheet: List<TimesheetsModel>) {
        val timesheetEntries: ArrayList<Any> = ArrayList()

        setProgressBarStatus(false)
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

    private fun setProgressBarStatus(status: Boolean) = when (status) {
        true -> bar_progress.visibility = View.VISIBLE
        false -> bar_progress.visibility = View.GONE
    }
}
