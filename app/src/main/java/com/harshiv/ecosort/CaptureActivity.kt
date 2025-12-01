package com.harshiv.ecosort

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import org.json.JSONArray


class CaptureActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    private lateinit var tvCount: TextView
    private lateinit var tvMotion: TextView

    private var itemCount = 0

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capture)

        tvCount = findViewById(R.id.tvItemCount)
        tvMotion = findViewById(R.id.tvMotionStatus)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Load saved count
        val prefs = getSharedPreferences("EcoSortPrefs", MODE_PRIVATE)
        itemCount = prefs.getInt("itemCount", 0)

        updateUI()
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { accel ->
            sensorManager.registerListener(
                this,
                accel,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    override fun onPause() {
        super.onPause()
        saveSession()
        saveCount()
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val dx = abs(x - lastX)
        val dy = abs(y - lastY)
        val dz = abs(z - lastZ)

        lastX = x
        lastY = y
        lastZ = z

        val motionDetected = dx > 1f || dy > 1f || dz > 1f

        if (motionDetected) {
            itemCount++
            saveCount()
            tvMotion.text = "Motion Detected"
            updateUI()
        } else {
            tvMotion.text = "No Motion"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun updateUI() {
        tvCount.text = "Items Sorted: $itemCount"
    }

    private fun saveCount() {
        val prefs = getSharedPreferences("EcoSortPrefs", MODE_PRIVATE)
        prefs.edit().putInt("itemCount", itemCount).apply()
    }
    private fun saveSession() {
        val prefs = getSharedPreferences("EcoSortPrefs", MODE_PRIVATE)
        val sessionListJson = prefs.getString("sessionHistory", "[]")

        val jsonArray = JSONArray(sessionListJson)
        jsonArray.put(itemCount)   // add current session count

        prefs.edit().putString("sessionHistory", jsonArray.toString()).apply()
    }

}
