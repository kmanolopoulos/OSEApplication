package com.kmanolopoulos.oseapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        findViewById<Button>(R.id.btn_main_search).setOnClickListener() { onChoice(it) }
        findViewById<Button>(R.id.btn_main_favourite).setOnClickListener() { onChoice(it) }
        findViewById<Button>(R.id.btn_main_about).setOnClickListener() { onChoice(it) }
    }

    fun onChoice(view: View) {
        var intent : Intent

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
