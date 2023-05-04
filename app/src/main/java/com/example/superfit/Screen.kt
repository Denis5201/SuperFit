package com.example.superfit

sealed class Screen(val route: String) {
    object SignInScreen : Screen("login")
    object SignUpScreen : Screen("registration")
    object MainScreen : Screen("main")
    object BodyScreen : Screen("body")
    object TrainingScreen : Screen("training")
    object ProgressScreen : Screen("progress")
    object StatisticsScreen : Screen("statistics")
    object ImagesScreen : Screen("images")
}
