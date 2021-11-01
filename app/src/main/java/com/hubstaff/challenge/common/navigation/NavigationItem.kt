package com.hubstaff.challenge.common.navigation

import androidx.navigation.compose.NamedNavArgument

interface NavigationItem {
    val args: List<NamedNavArgument>
    val destination: String
}