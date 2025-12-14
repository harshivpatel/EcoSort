package com.harshiv.ecosort.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.harshiv.ecosort.storage.Session
import com.harshiv.ecosort.storage.SessionFileManager

@Composable
fun StatsScreen(navController: NavController) {

    val context = LocalContext.current
    var sessions by remember {
        mutableStateOf(SessionFileManager.readSessions(context))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Statistics", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                SessionFileManager.clearSessions(context)
                sessions = emptyList()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Session History")
        }

        Spacer(Modifier.height(16.dp))

        if (sessions.isEmpty()) {
            Text(
                text = "No sessions recorded yet",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(sessions) { index, session ->
                    SessionCard(index + 1, session)
                }
            }
        }
    }
}

@Composable
private fun SessionCard(index: Int, session: Session) {
    Card {
        Column(Modifier.padding(16.dp)) {
            Text("Session $index", style = MaterialTheme.typography.titleMedium)
            Text("Motions detected: ${session.motionCount}")
            Text("Date: ${session.timestamp}")
        }
    }
}
