package com.kmanolopoulos.oseapplication.timesheet

data class TimesheetQuery(
    val stationFrom: String,
    val stationTo: String,
    val date: String
)