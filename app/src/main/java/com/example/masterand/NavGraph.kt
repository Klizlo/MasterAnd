package com.example.masterand

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.masterand.viewModel.ProfileViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, profileViewModel: ProfileViewModel) {

    NavHost(navController = navController, startDestination = "login_screen",
        enterTransition = {
            fadeIn(
                animationSpec = tween(1000, easing = EaseIn)
            ) + slideIntoContainer(
                animationSpec = tween(1000, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        exitTransition =  {
            fadeOut(
                animationSpec = tween(1000, easing = EaseOut)
            ) + slideOutOfContainer(
                animationSpec = tween(1000, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }
    ) {
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController, profileViewModel = profileViewModel)
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