package com.example.superfit

sealed class Screen(val route: String) {
    object SignInScreen : Screen("login")
    object SignUpScreen : Screen("registration")
    object MainScreen : Screen("main")
}
