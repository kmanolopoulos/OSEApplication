package com.kmanolopoulos.oseapplication.timesheet

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.kmanolopoulos.oseapplication.databases.DataFileBrowser
import com.kmanolopoulos.oseapplication.main.MainActivity
import com.kmanolopoulos.oseapplication.R
import java.io.File


class TimesheetDownload(context: Context) {
    private val dldContext = context
    private val dldTimesheet = "https://v-pigadas.herokuapp.com/api/ose/routes"
    private val dldFileString = "/Timesheet.json"
    private val dldFileClass = File(context.getExternalFilesDir(null), dldFileString)
    private var dldId = 0L

    //
    // Broadcast receiver instance to get "finished download" intents and filter
    // instances of finished download that we start below
    //
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)) {
                dldId -> {
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
        dldFileClass.delete()

        // Download request attributes
        val request =
            DownloadManager.Request(Uri.parse(dldTimesheet))
                .setTitle("Synchronization")                                           // Title of the Download Notification
                .setDescription("Retrieving timesheets")                               // Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(dldFileClass))                         // Uri of the destination file
                .setAllowedOverMetered(true)                                           // Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true)                                           // Set if download is allowed on roaming network

        // Download manager instance
        val manager =
            getSystemService(dldContext, DownloadManager::class.java) as DownloadManager

        // Enqueuing and starting download
        dldId = manager.enqueue(request)

        // Show progress bar during download
        (dldContext as MainActivity).setProgressStatus(true, dldContext.resources.getString(
            R.string.downloading
        ))

        // Register a listener for catching download finish
        dldContext.registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
    }

    //
    // Method with actions when downloading of JSON file with timesheets is completed
    //
    fun endDownload() {
        // Unregister the listener for catching download finish
        dldContext.unregisterReceiver(onDownloadComplete)

        // Update progress bar after download & before process
        (dldContext as MainActivity).setProgressStatus(true, dldContext.resources.getString(
            R.string.processing
        ))

        // Update database with JSON data
        val message = DataFileBrowser(
            dldContext
        )
            .parseJsonData(dldFileClass)

        // Hide progress bar after process
        dldContext.setProgressStatus(false, "")

        // Remove temporary file after it was used
        dldFileClass.delete()

        // Show message of synchronization
        Toast.makeText(dldContext, message, Toast.LENGTH_SHORT).show()
    }
}