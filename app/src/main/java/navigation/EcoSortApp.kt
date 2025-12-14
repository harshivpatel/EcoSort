package com.harshiv.ecosort.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoSortApp() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EcoSort") }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.Capture.route) { CaptureScreen(navController) }
            composable(Screen.Stats.route) { StatsScreen(navController) }
            composable(Screen.Tips.route) { TipsScreen(navController) }
        }
    }
}
