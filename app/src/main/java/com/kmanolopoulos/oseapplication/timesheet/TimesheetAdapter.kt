package com.kmanolopoulos.oseapplication.timesheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmanolopoulos.oseapplication.R

class TimesheetAdapter(val items: ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)

        when (viewType) {
            R.layout.holder_timesheet_data -> {
                return TimesheetDataHolder(
                    view
                )
            }
            /*R.layout.holder_timesheet_data*/
            else -> {
                return TimesheetHeaderHolder(
                    view
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val obj = items[position]

        when (holder) {
            is TimesheetDataHolder -> {
                holder.stationFrom.text = (obj as TimesheetDataEntry).stationFrom
                holder.stationTo.text = obj.stationTo
                holder.timeFrom.text = obj.timeFrom
                holder.timeTo.text = obj.timeTo
            }
            is TimesheetHeaderHolder -> {

            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when {
        position != 0 -> R.layout.holder_timesheet_data
        else -> R.layout.holder_timesheet_header
    }
}