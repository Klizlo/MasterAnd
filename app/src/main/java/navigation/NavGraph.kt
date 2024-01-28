package navigation

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
import com.example.masterand.Screens.GameMainScreen
import com.example.masterand.Screens.LoginScreen
import com.example.masterand.Screens.ProfileScreen
import com.example.masterand.Screens.ResultScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {

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
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.Profile.route + "/{profileId}/{colorNumber}?uri={uri}",
            arguments = listOf(
                navArgument("uri") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                },
                navArgument("profileId") {type = NavType.LongType},
                navArgument("colorNumber") {type = NavType.IntType}
        )) {
            backStackEntry ->

            val uri = backStackEntry.arguments?.getString("uri")
            val profileId = backStackEntry.arguments?.getLong("profileId")!!
            val number = backStackEntry.arguments?.getInt("colorNumber")!!

            ProfileScreen(
                    navController = navController,
                    uri = uri,
                    profileId = profileId,
                    number = number
                )
        }

        composable(route = Screen.Game.route + "/{profileId}/{colorNumber}?uri={uri}",
            arguments = listOf(
                navArgument("colorNumber") {type = NavType.IntType},
                navArgument("profileId") {type = NavType.LongType},
                navArgument("uri") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
        )) {
            backStackEntry ->

            val number = backStackEntry.arguments?.getInt("colorNumber")!!
            val profileId = backStackEntry.arguments?.getLong("profileId")!!
            val uri = backStackEntry.arguments?.getString("uri")

            GameMainScreen(navController = navController, number = number, profileId = profileId, uri = uri)
        }

        composable(route = Screen.Results.route + "/{profileId}/{colorNumber}/{recentScore}?uri={uri}",
            arguments = listOf(
                navArgument("profileId") { type = NavType.LongType },
                navArgument("colorNumber") { type = NavType.IntType },
                navArgument("recentScore") {type = NavType.IntType},
                navArgument("uri") {
                    type = NavType.StringType
                    defaultValue = null
                    nullable = true
                }
        )) {
            backStackEntry -> 
            
            val profileId = backStackEntry.arguments?.getLong("profileId")!!
            val colorNumber = backStackEntry.arguments?.getInt("colorNumber")!!
            val recentScore = backStackEntry.arguments?.getInt("recentScore")!!
            val uri = backStackEntry.arguments?.getString("uri")
            
            ResultScreen(navController = navController, profileId = profileId, colorNumber = colorNumber, recentScore = recentScore, uri = uri)
        }
    }
}