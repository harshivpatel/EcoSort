package com.harshiv.ecosort.storage

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object SessionFileManager {

    private const val FILE_NAME = "sessions.txt"

    fun saveSession(context: Context, motionCount: Int) {
        val timestamp = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())

        val file = File(context.filesDir, FILE_NAME)
        file.appendText("$motionCount,$timestamp\n")
    }

    fun readSessions(context: Context): List<Session> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return emptyList()

        return file.readLines().mapNotNull { line ->
            val parts = line.split(",")
            if (parts.size == 2) {
                Session(
                    motionCount = parts[0].toIntOrNull() ?: return@mapNotNull null,
                    timestamp = parts[1]
                )
            } else null
        }
    }

    fun clearSessions(context: Context) {
        val file = File(context.filesDir, FILE_NAME)
        if (file.exists()) file.delete()
    }
}
