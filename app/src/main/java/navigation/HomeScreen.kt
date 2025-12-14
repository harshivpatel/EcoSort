package com.harshiv.ecosort.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Welcome to EcoSort", style = MaterialTheme.typography.headlineMedium)

        Text("EcoSort tracks sorting gestures and stores session history locally.")

        Button(onClick = { navController.navigate(Screen.Capture.route) }) {
            Text("Capture (Sort)")
        }

        Button(onClick = { navController.navigate(Screen.Stats.route) }) {
            Text("Stats & History")
        }

        Button(onClick = { navController.navigate(Screen.Tips.route) }) {
            Text("Recycling Tips")
        }
    }
}
