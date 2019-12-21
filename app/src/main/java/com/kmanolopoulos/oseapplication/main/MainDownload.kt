package com.kmanolopoulos.oseapplication.main

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.kmanolopoulos.oseapplication.R
import com.kmanolopoulos.oseapplication.models.ParsingModel
import com.kmanolopoulos.oseapplication.models.StationsModel
import com.kmanolopoulos.oseapplication.models.TimesheetsModel
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream


class MainDownload(val context: Context) {

    // Timesheet JSON data variables
    private var dldTimesheetId = 0L
    private val dldTimesheetURL = "https://v-pigadas.herokuapp.com/api/ose/routes"
    private val dldTimesheetFileString = "/Timesheet.json"
    private val dldTimesheetFileClass =
        File(context.getExternalFilesDir(null), dldTimesheetFileString)

    //
    // Broadcast receiver instance to get "finished download" intents and filter
    // instances of finished download that we start below
    //
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)) {
                dldTimesheetId -> {
                    endDownload()
                }
                else -> {
                }
            }
        }
    }

    //
    // Method to start downloading of JSON file with timesheets
    //
    fun startDownload() {
        // Remove temporary file if it exists
        dldTimesheetFileClass.delete()

        // Download request attributes
        val dldTimesheetRequest =
            DownloadManager.Request(Uri.parse(dldTimesheetURL))
                .setTitle("Synchronization")                                           // Title of the Download Notification
                .setDescription("Retrieving timesheets")                               // Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(dldTimesheetFileClass))                // Uri of the destination file
                .setAllowedOverMetered(true)                                           // Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true)                                           // Set if download is allowed on roaming network

        // Download manager instance
        val manager =
            getSystemService(context, DownloadManager::class.java) as DownloadManager

        // Enqueuing and starting download
        dldTimesheetId = manager.enqueue(dldTimesheetRequest)

        // Show progress bar during download
        (context as MainActivity).setProgressStatus(
            true,
            context.resources.getString(R.string.downloading)
        )

        // Register a listener for catching download finish
        context.registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    //
    // Method with actions when downloading of JSON file with timesheets is completed
    //
    fun endDownload() {
        // Unregister the listener for catching download finish
        context.unregisterReceiver(onDownloadComplete)

        // Update progress bar after download & before process
        (context as MainActivity).setProgressStatus(
            true,
            context.resources.getString(R.string.processing)
        )

        // Update database with Stations JSON data
        val resultStations = getStations()

        // Update database with Timesheets JSON data
        val resultTimesheets = getTimesheets()

        // Hide progress bar after process
        context.setProgressStatus(false, "")

        // Remove temporary file after it was used
        dldTimesheetFileClass.delete()

        // Get message to be displayed in the next toast
        val message = getMessage(resultStations, resultTimesheets)

        // Show message of synchronization
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    //
    // Method to read stations from JSON file and store them to database
    //
    private fun getStations(): ParsingModel {
        var jsonData: String
        var jsonFileStream: InputStream

        try {
            jsonFileStream = context.resources.openRawResource(R.raw.stations)
        } catch (exception: Resources.NotFoundException) {
            return ParsingModel(context, ParsingModel.Values.FILE_NOT_DOWNLOADED)
        }

        jsonData = jsonFileStream.bufferedReader().use { it.readText() }

        return StationsModel(context).parseJsonData(jsonData)
    }

    //
    // Method to read stations from JSON file and store them to database
    //
    private fun getTimesheets(): ParsingModel {
        var jsonData: String
        var jsonFileStream: InputStream

        try {
            jsonFileStream = dldTimesheetFileClass.inputStream()
        } catch (exception: FileNotFoundException) {
            return ParsingModel(context, ParsingModel.Values.FILE_NOT_DOWNLOADED)
        }

        jsonData = jsonFileStream.bufferedReader().use { it.readText() }

        return TimesheetsModel(context).parseJsonData(jsonData)
    }

    //
    // Method to compute message that describes the result of the synchronization process
    //
    private fun getMessage(resultStations: ParsingModel, resultTimesheets: ParsingModel): String =
        when (resultStations.result) {
            ParsingModel.Values.FILE_OK -> {
                when (resultTimesheets.result) {
                    ParsingModel.Values.FILE_OK -> resultTimesheets.getMessage()
                    ParsingModel.Values.FILE_NOT_DOWNLOADED -> resultTimesheets.getMessage()
                    ParsingModel.Values.FILE_WRONG_FORMAT -> resultTimesheets.getMessage()
                }
            }
            ParsingModel.Values.FILE_NOT_DOWNLOADED -> resultStations.getMessage()
            ParsingModel.Values.FILE_WRONG_FORMAT -> resultStations.getMessage()
        }
}