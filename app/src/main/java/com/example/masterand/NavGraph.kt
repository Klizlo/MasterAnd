package com.example.masterand

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "login_screen") {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.Profile.route + "?uri={uri}&username={username}&email={email}&number={number}",
            arguments = listOf(
                navArgument("uri") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                },
                navArgument("username") {type = NavType.StringType},
                navArgument("email") {type = NavType.StringType},
                navArgument("number") {type = NavType.IntType}
        )) {
            backStackEntry ->

            val uri = backStackEntry.arguments?.getString("uri")
            val username = backStackEntry.arguments?.getString("username")
            val email = backStackEntry.arguments?.getString("email")
            val number = backStackEntry.arguments?.getInt("number")

            ProfileScreen(
                    navController = navController,
                    uri = uri,
                    username = username,
                    email = email,
                    number = number
                )
        }

        composable(route = Screen.Game.route + "?number={number}", arguments = listOf(navArgument("number") {type = NavType.IntType})) {
            backStackEntry ->  GameMainScreen(navController = navController, backStackEntry.arguments?.getInt("number"))
        }
    }
}