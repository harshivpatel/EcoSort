package com.harshiv.ecosort.navigation

import android.content.Context
import android.hardware.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.harshiv.ecosort.storage.SessionFileManager
import kotlin.math.abs
import kotlin.math.sqrt

@Composable
fun CaptureScreen(navController: NavController) {

    val context = LocalContext.current
    val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // UI state
    var motionCount by remember { mutableStateOf(0) }
    var sessionEnded by remember { mutableStateOf(false) }

    // Sensor state (NOT Compose state)
    var gravity = FloatArray(3)
    var lastTriggerTime = 0L

    DisposableEffect(Unit) {

        if (accelerometer == null) {
            // No sensor available (rare, but safe)
            onDispose { }
            return@DisposableEffect onDispose { }
        }

        val listener = object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent) {
                if (sessionEnded) return

                // High-pass filter to remove gravity
                val alpha = 0.8f
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0]
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1]
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2]

                val x = event.values[0] - gravity[0]
                val y = event.values[1] - gravity[1]
                val z = event.values[2] - gravity[2]

                val acceleration = sqrt(x * x + y * y + z * z)

                // Threshold tuned for emulator + real phone
                val threshold = 2.2f
                val now = System.currentTimeMillis()

                // Prevent rapid multiple counts
                if (acceleration > threshold && now - lastTriggerTime > 500) {
                    motionCount++
                    lastTriggerTime = now
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(
            listener,
            accelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Capture Session",
            style = MaterialTheme.typography.headlineMedium
        )

        Card {
            Column(Modifier.padding(16.dp)) {
                Text("Motion Detection Active")
                Text("Events detected: $motionCount")
            }
        }

        Button(
            onClick = {
                if (!sessionEnded && motionCount > 0) {
                    SessionFileManager.saveSession(
                        context = context,
                        motionCount = motionCount
                    )
                    sessionEnded = true
                }
                navController.navigate(Screen.Stats.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Stop Session")
        }
    }
}
