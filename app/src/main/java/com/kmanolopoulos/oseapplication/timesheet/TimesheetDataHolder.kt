package com.kmanolopoulos.oseapplication.timesheet

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.holder_timesheet_data.view.*

class TimesheetDataHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val stationFrom = view.txt_search_station_from
    val stationTo = view.txt_search_station_to
    val timeFrom = view.txt_search_time_from
    val timeTo = view.txt_search_time_to
}