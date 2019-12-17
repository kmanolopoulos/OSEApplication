package com.kmanolopoulos.oseapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setProgressStatus(false, "")
        findViewById<Button>(R.id.btn_main_search).setOnClickListener { onChoice(it) }
        findViewById<Button>(R.id.btn_main_synchronize).setOnClickListener { onChoice(it) }
        findViewById<Button>(R.id.btn_main_favourite).setOnClickListener { onChoice(it) }
        findViewById<Button>(R.id.btn_main_about).setOnClickListener { onChoice(it) }
    }

    private fun onChoice(view: View) {
        val intent: Intent

        when (view.id) {
            R.id.btn_main_search -> {
                intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_main_synchronize -> {
                TimesheetDownload(this).startDownload()
            }
            R.id.btn_main_favourite -> {

            }
            R.id.btn_main_about -> {

            }
        }
    }

    fun setProgressStatus(status: Boolean, message: String) {
        when (status) {
            false -> {
                findViewById<ProgressBar>(R.id.bar_main_progress_bar).visibility = View.GONE
                findViewById<TextView>(R.id.txt_main_progress_message).visibility = View.GONE
            }
            true -> {
                findViewById<ProgressBar>(R.id.bar_main_progress_bar).visibility = View.VISIBLE
                findViewById<TextView>(R.id.txt_main_progress_message).visibility = View.VISIBLE
            }
        }

        findViewById<TextView>(R.id.txt_main_progress_message).text = message
    }

}
