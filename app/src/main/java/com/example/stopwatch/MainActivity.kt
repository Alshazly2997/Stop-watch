package com.example.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var stopWatch: Chronometer//The stopwatch
    var running = false//Is the stopwatch running?
    var offset: Long = 0 //The base offset for the stopwatch

    //Add key strings for use with the bundle
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get reference to the stopwatch
        stopWatch = findViewById<Chronometer>(R.id.stopwatch)

        //Restore the previous state
        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                stopWatch.base = savedInstanceState.getLong(BASE_KEY)
                stopWatch.start()
            } else setBaseTime()
        }
        //Get reference to the Buttons
        val startButton = findViewById<Button>(R.id.start_button)
        val pauseButton = findViewById<Button>(R.id.pause_button)
        val resetButton = findViewById<Button>(R.id.reset_button)

        //The start button start the stopwatch if it's not running
        startButton.setOnClickListener {
            if (!running) {
                setBaseTime()
                stopWatch.start()
                running = true
            }
        }

        //The pause button pauses the stopwatch if it's running
        pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                stopWatch.stop()
                running = false
            }
        }

        //The reset button sets the offset and stopwatch to 0
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, stopWatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    //Update the stopwatch.base time, allowing for any offset
    private fun setBaseTime(){
        stopWatch.base = SystemClock.elapsedRealtime() - offset
    }

    //Record the offset
    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopWatch.base
    }
}