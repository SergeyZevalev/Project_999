package com.example.project_999

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.StringRes

class MainActivity : AppCompatActivity() {


    private lateinit var representative: MainRepresentative
    private lateinit var uiObserver: UiObserver<Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        representative = (application as MyApp).mainRepresentative

        val textView = findViewById<TextView>(R.id.text_view)
        uiObserver = object : UiObserver<Int> {
            override fun update(data: Int) = runOnUiThread {
                textView.setText(data)
            }

        }

        if (savedInstanceState == null) {
            textView.text = "0"
        }

        textView.setOnClickListener {
            representative.startAsync()
        }
    }

    override fun onResume() {
        super.onResume()
        representative.startGettingUpdates(uiObserver)
    }

    override fun onPause() {
        super.onPause()
        representative.stopGettingUpdates()
    }


}


