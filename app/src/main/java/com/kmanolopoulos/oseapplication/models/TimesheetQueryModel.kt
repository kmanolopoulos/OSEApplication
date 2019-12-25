package com.kmanolopoulos.oseapplication.models

data class TimesheetQueryModel(
    val stationFrom: StationsModel,
    val stationTo: StationsModel,
    val date: String
)