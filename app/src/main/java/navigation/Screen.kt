package com.harshiv.ecosort.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Capture : Screen("capture", "Capture", Icons.Default.PlayArrow)
    object Stats : Screen("stats", "Stats", Icons.Default.BarChart)
    object Tips : Screen("tips", "Tips", Icons.Default.Info)
}
