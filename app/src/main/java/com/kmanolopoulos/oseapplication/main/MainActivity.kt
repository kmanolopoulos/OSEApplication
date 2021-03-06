package com.kmanolopoulos.oseapplication.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kmanolopoulos.oseapplication.R
import com.kmanolopoulos.oseapplication.search.SearchActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        findViewById<Button>(R.id.btn_main_search).setOnClickListener { onChoice(it) }
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
            R.id.btn_main_favourite -> {

            }
            R.id.btn_main_about -> {

            }
        }
    }
}
