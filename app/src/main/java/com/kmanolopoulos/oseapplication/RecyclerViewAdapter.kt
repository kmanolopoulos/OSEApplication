package com.kmanolopoulos.oseapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter :
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
        TODO("not implemented")
    }

    override fun getItemCount(): Int {
        TODO("not implemented")
    }
}