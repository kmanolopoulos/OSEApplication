package com.kmanolopoulos.oseapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(val items : ArrayList<TimesheetEntry>) :
    RecyclerView.Adapter<RecyclerViewAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_timesheet_recyclerview, parent, false)

        return RecyclerViewAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapterViewHolder, position: Int) {
        holder.stationFrom.text = items.get(position).stationFrom
        holder.stationTo.text = items.get(position).stationTo
        holder.timeFrom.text = items.get(position).timeFrom
        holder.timeTo.text = items.get(position).timeTo
    }

    override fun getItemCount(): Int {
        return items.size
    }
}