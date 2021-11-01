package com.hubstaff.challenge.common.navigation

import androidx.navigation.compose.NamedNavArgument

object NavigationDirections {

    val login = object: NavigationItem {
        override val args = emptyList<NamedNavArgument>()
        override val destination: String by lazy { LOGIN }
    }

    val timer = object: NavigationItem {
        override val args = emptyList<NamedNavArgument>()
        override val destination: String by lazy { TIMER }
    }

    private const val LOGIN = "login"
    private const val TIMER = "timer"
}