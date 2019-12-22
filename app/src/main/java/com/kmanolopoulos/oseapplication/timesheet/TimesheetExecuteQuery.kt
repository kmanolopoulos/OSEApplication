package com.kmanolopoulos.oseapplication.timesheet

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.core.content.ContextCompat
import com.kmanolopoulos.oseapplication.models.TimesheetsModel
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class TimesheetExecuteQuery(val context: Context) {
    private var dldTimesheetId = 0L
    private val dldTimesheetFileString = "/Timesheet.json"
    private val dldTimesheetFileClass =
        File(context.getExternalFilesDir(null), dldTimesheetFileString)
    private var timesheet: MutableList<TimesheetsModel> = mutableListOf()

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
    fun startDownload(query: TimesheetQuery) {

        val dldTimesheetURL =
            "https://extranet.trainose.gr/epivatikos/public_ticketing/ajax.php?c=dromologia&op=vres_dromologia&apo="
                .plus(query.stationFrom)
                .plus("&pros=")
                .plus(query.stationTo)
                .plus("&date=")
                .plus(query.date)
                .plus("&rtn_date=")
                .plus(query.date)
                .plus("&travel_type=1&calculate_costs=1&trena%5B%5D=apla&trena%5B%5D=ic&trena%5B%5D=ice&trena%5B%5D=bed&time=23%3A59&time_type=anaxwrhsh&rtn_time=23%3A59&rtn_time_type=anaxwrhsh")

        // Remove temporary file if it exists
        dldTimesheetFileClass.delete()

        // Download request attributes
        val dldTimesheetRequest =
            DownloadManager.Request(Uri.parse(dldTimesheetURL))
                .setTitle("Timesheet Query")                                           // Title of the Download Notification
                .setDescription("Retrieving timesheets")                               // Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(dldTimesheetFileClass))                // Uri of the destination file
                .setAllowedOverMetered(true)                                           // Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true)                                           // Set if download is allowed on roaming network

        // Download manager instance
        val manager =
            ContextCompat.getSystemService(context, DownloadManager::class.java) as DownloadManager

        // Enqueuing and starting download
        dldTimesheetId = manager.enqueue(dldTimesheetRequest)

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

        // Send back timesheets results
        getTimesheets()

        // Remove temporary file after it was used
        dldTimesheetFileClass.delete()
    }

    //
    // Method to read stations from JSON file and store them to database
    //
    private fun getTimesheets() {
        var jsonData: String
        var jsonFileStream: InputStream

        try {
            jsonFileStream = dldTimesheetFileClass.inputStream()
        } catch (exception: FileNotFoundException) {
            return
        }

        jsonData = jsonFileStream.bufferedReader().use { it.readText() }

        parseJsonData(jsonData)


    }

    // Method to parse JSON data (TODO)
    private fun parseJsonData(jsonData: String) {
        timesheet = mutableListOf(
            TimesheetsModel("IC50", "20-12-2012", "ΑΘΗΝ", "11:52", "ΘΕΣΣ", "17:34"),
            TimesheetsModel("IC50", "20-12-2012", "ΑΘΗΝ", "12:52", "ΘΕΣΣ", "18:34"),
            TimesheetsModel("IC50", "20-12-2012", "ΑΘΗΝ", "13:52", "ΘΕΣΣ", "19:34")
        )
        (context as TimesheetActivity).onResultsFound(timesheet)
    }
}