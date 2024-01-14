package com.example.masterand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.masterand.database.MasterAndDatabase
import com.example.masterand.repository.ProfileRepository
import com.example.masterand.repository.ProfileRepositoryImpl
import com.example.masterand.ui.theme.MasterAndTheme
import com.example.masterand.viewModel.ProfileVieModelFactory
import com.example.masterand.viewModel.ProfileViewModel

class MainActivity : ComponentActivity() {

    lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val database : MasterAndDatabase = MasterAndDatabase.getDatabase(LocalContext.current)
            val profileRepository: ProfileRepository = ProfileRepositoryImpl(database.getProfileDao())

            val profileViewModel : ProfileViewModel = ViewModelProvider(this, ProfileVieModelFactory(profileRepository)).get(
                ProfileViewModel::class.java)

            navHostController = rememberNavController()
            
            SetupNavGraph(navController = navHostController, profileViewModel = profileViewModel)
        }
    }
}