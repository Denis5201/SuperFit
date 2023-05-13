package com.example.superfit

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.superfit.presentation.auth.SignInScreen
import com.example.superfit.presentation.auth.SignInViewModel
import com.example.superfit.presentation.auth.SignUpScreen
import com.example.superfit.presentation.auth.SignUpViewModel
import com.example.superfit.presentation.body.BodyScreen
import com.example.superfit.presentation.body.BodyViewModel
import com.example.superfit.presentation.graphics.ProgressScreen
import com.example.superfit.presentation.graphics.ProgressViewModel
import com.example.superfit.presentation.graphics.StatisticsScreen
import com.example.superfit.presentation.graphics.StatisticsViewModel
import com.example.superfit.presentation.images.ImagesScreen
import com.example.superfit.presentation.images.ImagesViewModel
import com.example.superfit.presentation.main.MainScreen
import com.example.superfit.presentation.main.MainViewModel
import com.example.superfit.presentation.training.TrainingScreen
import com.example.superfit.presentation.training.TrainingViewModel

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
        composable(
            route = "${Screen.BodyScreen.route}/{${Constants.WEIGHT}}/{${Constants.HEIGHT}}",
            arguments = listOf(
                navArgument(Constants.WEIGHT) { type = NavType.StringType },
                navArgument(Constants.HEIGHT) { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<BodyViewModel>()
            BodyScreen(navController, viewModel)
        }
        composable(
            route = "${Screen.TrainingScreen.route}/{${Constants.TRAINING}}",
            arguments = listOf(
                navArgument(Constants.TRAINING) { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<TrainingViewModel>()
            TrainingScreen(navController, viewModel)
        }
        composable(Screen.ProgressScreen.route) {
            val viewModel = hiltViewModel<ProgressViewModel>()
            ProgressScreen(navController, viewModel)
        }
        composable(Screen.StatisticsScreen.route) {
            val viewModel = hiltViewModel<StatisticsViewModel>()
            StatisticsScreen(navController, viewModel)
        }
        composable(Screen.ImagesScreen.route) {
            val viewModel = hiltViewModel<ImagesViewModel>()
            ImagesScreen(navController, viewModel)
        }
    }
}