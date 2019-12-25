package com.kmanolopoulos.oseapplication.search

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kmanolopoulos.oseapplication.R
import com.kmanolopoulos.oseapplication.models.StationsModel
import com.kmanolopoulos.oseapplication.timesheet.TimesheetActivity
import kotlinx.android.synthetic.main.activity_search.*
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : AppCompatActivity() {
    private var stationsList: List<StationsModel> = mutableListOf()
    private var stationsMap: MutableMap<String, StationsModel> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Click listener for "search" button
        btn_search_search.setOnClickListener { onChoice(it) }

        // Click listener for "date" button
        txt_search_date.setOnClickListener { onChoice(it) }

        // Instantiate station data
        stationsList = SearchStations(this).getStations()

        // Isolate station names to a list
        stationsList.forEach { stationsMap[it.labelEn] = it }

        // Set suggestions for "from" view
        atv_search_from.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                ArrayList(stationsMap.keys)
            )
        )

        // Set suggestions for "to" view
        atv_search_to.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                ArrayList(stationsMap.keys)
            )
        )
    }

    //
    // Click listener for all choices
    //
    private fun onChoice(view: View) {

        when (view.id) {
            R.id.btn_search_search -> {
                if (performValidation()) {

                    val fromStation = stationsMap[atv_search_from.text.toString()]
                    val toStation = stationsMap[atv_search_to.text.toString()]

                    val intent = Intent(this, TimesheetActivity::class.java)

                    intent.putExtra(
                        "TimesheetActivity.SEARCH_FROM",
                        fromStation
                    )
                    intent.putExtra(
                        "TimesheetActivity.SEARCH_TO",
                        toStation
                    )
                    intent.putExtra(
                        "TimesheetActivity.SEARCH_DATE",
                        txt_search_date.text.toString()
                    )
                    startActivity(intent)
                } else {
                    val toast =
                        Toast.makeText(this, getString(R.string.invalid_data), Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
            R.id.txt_search_date -> {

                val calendar = Calendar.getInstance()

                val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    txt_search_date.text = sdf.format(calendar.time)
                }

                DatePickerDialog(
                    this,
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    // Validation checks:
    // Search from should be in Stations list
    // Search to should be in Stations list
    // Search from and search to should not match
    // Date should be valid
    private fun performValidation(): Boolean {

        // Check
        if ((atv_search_from.text.toString() !in stationsMap.keys) ||
            (atv_search_to.text.toString() !in stationsMap.keys) ||
            (atv_search_from.text.toString() == atv_search_to.text.toString()) ||
            (txt_search_date.text.toString().isEmpty())
        ) {
            return false
        }

        return true
    }

}
