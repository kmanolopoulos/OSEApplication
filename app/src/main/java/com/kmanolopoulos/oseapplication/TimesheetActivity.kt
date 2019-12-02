package com.kmanolopoulos.oseapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_timesheet.*

class TimesheetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timesheet)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val timesheetEntries: ArrayList<TimesheetEntry> = ArrayList()

        timesheetEntries.add(TimesheetEntry("Alexandroupoli","Thessaloniki","12:52","16:28"))
        timesheetEntries.add(TimesheetEntry("Alexandroupoli","Thessaloniki","13:52","17:28"))
        timesheetEntries.add(TimesheetEntry("Alexandroupoli","Thessaloniki","14:52","18:28"))
        timesheetEntries.add(TimesheetEntry("Alexandroupoli","Thessaloniki","15:52","19:28"))

        rec_search_layout.layoutManager = LinearLayoutManager(this)
        rec_search_layout.adapter = RecyclerViewAdapter(timesheetEntries)

        //Log.d("Ose Application", intent.getStringExtra("TimesheetActivity.SEARCH_FROM"))
        //Log.d("Ose Application", intent.getStringExtra("TimesheetActivity.SEARCH_TO"))
        //Log.d("Ose Application", intent.getStringExtra("TimesheetActivity.SEARCH_DATE"))
    }
}
