package com.stopwatch.app.common.navigation

import androidx.navigation.compose.NamedNavArgument

interface NavigationItem {
    val args: List<NamedNavArgument>
    val destination: String
}