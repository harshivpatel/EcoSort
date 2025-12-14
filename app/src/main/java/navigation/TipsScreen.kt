package com.harshiv.ecosort.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TipsScreen(navController: NavController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Tips", style = MaterialTheme.typography.headlineMedium)

        Text("This screen links to external recycling guidance.")

        Button(
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.epa.ie/our-services/monitoring--assessment/waste/")
                )
                context.startActivity(intent)
            }
        ) {
            Text("Open EPA Recycling Tips")
        }
    }
}
