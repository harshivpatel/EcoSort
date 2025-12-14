package com.harshiv.ecosort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.harshiv.ecosort.navigation.EcoSortApp
import com.harshiv.ecosort.ui.theme.EcoSortTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            EcoSortTheme {
                EcoSortApp()
            }
        }
    }
}
