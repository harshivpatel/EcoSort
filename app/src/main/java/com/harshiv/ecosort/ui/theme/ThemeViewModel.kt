package com.harshiv.ecosort.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ThemeViewModel : ViewModel() {
    var isDark = mutableStateOf(false)
        private set

    fun toggleTheme() {
        isDark.value = !isDark.value
    }
}
