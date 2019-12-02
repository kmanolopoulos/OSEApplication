package com.kmanolopoulos.oseapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_timesheet_recyclerview.view.*

class RecyclerViewAdapterViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val stationFrom = view.txt_search_station_from
    val stationTo = view.txt_search_station_to
    val timeFrom = view.txt_search_time_from
    val timeTo = view.txt_search_time_to
}