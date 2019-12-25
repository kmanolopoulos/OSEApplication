package com.kmanolopoulos.oseapplication.models

import java.io.Serializable

data class StationsModel(
    val code: String,
    val labelEn: String,
    val labelGr: String,
    val latitude: Double,
    val longitude: Double
) : Serializable