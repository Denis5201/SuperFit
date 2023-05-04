package com.example.superfit

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.superfit.presentation.auth.SignInScreen
import com.example.superfit.presentation.auth.SignInViewModel
import com.example.superfit.presentation.auth.SignUpScreen
import com.example.superfit.presentation.auth.SignUpViewModel
import com.example.superfit.presentation.body.BodyScreen
import com.example.superfit.presentation.graphics.ProgressScreen
import com.example.superfit.presentation.graphics.StatisticsScreen
import com.example.superfit.presentation.images.ImagesScreen
import com.example.superfit.presentation.main.MainScreen
import com.example.superfit.presentation.main.MainViewModel
import com.example.superfit.presentation.training.TrainingScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.SignInScreen.route) {
        composable(Screen.SignInScreen.route) {
            val viewModel = hiltViewModel<SignInViewModel>()
            SignInScreen(navController, viewModel)
        }
        composable(Screen.SignUpScreen.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(navController, viewModel)
        }
        composable(Screen.MainScreen.route) {
            val viewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController, viewModel)
        }
        composable(Screen.BodyScreen.route) {
            BodyScreen(navController)
        }
        composable(Screen.TrainingScreen.route) {
            TrainingScreen(navController)
        }
        composable(Screen.ProgressScreen.route) {
            ProgressScreen(navController)
        }
        composable(Screen.StatisticsScreen.route) {
            StatisticsScreen(navController)
        }
        composable(Screen.ImagesScreen.route) {
            ImagesScreen(navController)
        }
    }
}