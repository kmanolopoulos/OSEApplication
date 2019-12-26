package com.kmanolopoulos.oseapplication.models

data class TimesheetQueryModel(
    val stationsMap: HashMap<String, StationsModel>,
    val stationFrom: String,
    val stationTo: String,
    val date: String
)