package com.example.masterand

sealed class Screen(val route: String) {
    object Login : Screen(route = "login_screen")
    object Profile: Screen(route = "profile_screen")
    object Game : Screen(route = "game_screen")
}
