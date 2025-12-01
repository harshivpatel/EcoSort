package com.harshiv.ecosort

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val tvStats = findViewById<TextView>(R.id.tvStats)
        val tvHistory = findViewById<TextView>(R.id.tvHistory)
        val btnReset = findViewById<Button>(R.id.btnReset)

        val prefs = getSharedPreferences("EcoSortPrefs", MODE_PRIVATE)
        val totalCount = prefs.getInt("itemCount", 0)
        tvStats.text = "Total Items Sorted: $totalCount"


        val sessionJson = prefs.getString("sessionHistory", "[]")
        val sessions = JSONArray(sessionJson)

        var total = 0
        for (i in 0 until sessions.length()) {
            total += sessions.getInt(i)
        }

        tvStats.text = "Total Items Sorted: $total"


        val builder = StringBuilder("Session History:\n\n")
        for (i in 0 until sessions.length()) {
            builder.append("Session ${i + 1}: ${sessions[i]} items\n")
        }
        tvHistory.text = builder.toString()

        btnReset.setOnClickListener {
            prefs.edit()
                .putInt("itemCount", 0)
                .putString("sessionHistory", "[]")
                .apply()

            tvStats.text = "Total Items Sorted: 0"
            tvHistory.text = "Session History:\n\n(no sessions yet)"
        }
    }
}
