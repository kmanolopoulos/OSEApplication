package com.kmanolopoulos.oseapplication.search

import android.content.Context
import android.content.res.Resources
import com.kmanolopoulos.oseapplication.R
import com.kmanolopoulos.oseapplication.models.StationsModel
import java.io.InputStream

class SearchStations(val context: Context) {
    private var station: MutableList<StationsModel> = mutableListOf()

    //
    // Initializer
    //
    init {
        parseStationsFile()
    }

    //
    // Start parsing of stations resource
    //
    private fun parseStationsFile() {
        var jsonData: String
        var jsonFileStream: InputStream

        try {
            jsonFileStream = context.resources.openRawResource(R.raw.stations)
        } catch (exception: Resources.NotFoundException) {
            return
        }

        jsonData = jsonFileStream.bufferedReader().use { it.readText() }

        parseJsonData(jsonData)

        return
    }

    //
    // Parse Json data to form station (TODO)
    //
    private fun parseJsonData(jsonData: String) {
        station = mutableListOf(
            StationsModel("ΑΘΗΝ", "Athens", "Αθήνα", 37.9926109, 23.7208405),
            StationsModel("ΘΕΣΣ", "Thessaloniki", "Θεσσαλονίκη", 40.6444092, 22.9297791),
            StationsModel("ΑΥΛΩ", "Avlona", "Αυλώνα", 38.2499809, 23.6956196)
        )
    }

    //
    // Return all stations as a list of StationsModel
    //
    fun getStations(): List<StationsModel> {
        return station
    }
}