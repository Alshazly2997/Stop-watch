package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch: Chronometer //The stopwatch
    var running = false //To know if the stopwatch is running or not
    var offset: Long = 0 //The base offset for the stopwatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //The elements of the layout
        stopwatch = findViewById(R.id.stopwatch)
        val startButton = findViewById<Button>(R.id.start_button)
        val pauseButton = findViewById<Button>(R.id.pause_button)
        val resetButton = findViewById<Button>(R.id.reset_button)

        //To start the stopwatch if it's not
        startButton.setOnClickListener {
            if (!running){
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }

        //To pause the stopwatch
        pauseButton.setOnClickListener {
            if (running){
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        //To reset the stopwatch
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    //Get the base time for the stopwatch
    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    //Record the Offset
    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
}