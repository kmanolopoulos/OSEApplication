package com.kmanolopoulos.oseapplication.search

import android.content.Context
import android.content.res.Resources
import com.kmanolopoulos.oseapplication.R
import com.kmanolopoulos.oseapplication.models.StationsModel
import org.json.JSONObject
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
        val jsonData: String
        val jsonFileStream: InputStream

        try {
            jsonFileStream = context.resources.openRawResource(R.raw.stations)
        } catch (exception: Resources.NotFoundException) {
            return
        }

        jsonData = jsonFileStream.bufferedReader().use { it.readText() }

        parseJsonData(jsonData)
    }

    //
    // Parse Json data to form stations data list
    //
    private fun parseJsonData(jsonData: String) {
        try {
            val jsonStationsArray =
                JSONObject(jsonData).getJSONObject("oseTravel").getJSONArray("stations")

            for (i in 0 until jsonStationsArray.length()) {
                val jsonStationObject = jsonStationsArray.getJSONObject(i)
                station.add(
                    StationsModel(
                        jsonStationObject.getString("code"),
                        jsonStationObject.getString("label_en"),
                        jsonStationObject.getString("label_gr"),
                        jsonStationObject.getDouble("latitude"),
                        jsonStationObject.getDouble("longitude")
                    )
                )
            }
        } catch (exception: Exception) {
            station.clear()
        }
    }

    //
    // Return all stations as a list of StationsModel
    //
    fun getStations(): List<StationsModel> {
        return station
    }
}