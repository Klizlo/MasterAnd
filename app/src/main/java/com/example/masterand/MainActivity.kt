package com.example.masterand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import navigation.SetupNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navHostController = rememberNavController()
            
            SetupNavGraph(navController = navHostController)
        }
    }
}