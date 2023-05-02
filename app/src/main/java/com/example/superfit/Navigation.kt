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
import com.example.superfit.presentation.main.MainScreen

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
            MainScreen()
        }
    }
}