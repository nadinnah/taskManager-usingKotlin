package com.example.taskmanager

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.tasks.home.Home
import com.example.taskmanager.tasks.login.LoginScreen
import com.example.taskmanager.tasks.login.LoginViewModel
import com.example.taskmanager.tasks.login.SignUpScreen

enum class LoginRoutes {
    Signup,
    SignIn
}

enum class HomeRoutes {
    Home, Details
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel
) {
    NavHost(navController = navController, startDestination = LoginRoutes.SignIn.name) {
        composable(route = LoginRoutes.SignIn.name) {
            LoginScreen(
                onNavToHomePage = {
                    navController.navigate(HomeRoutes.Home.name) {
                        launchSingleTop =
                            true //creates one instance in the backstack instead of multiple instances
                        popUpTo(route = LoginRoutes.SignIn.name) {
                            inclusive = true

                        }
                    }
                }
            ) {
                navController.navigate(LoginRoutes.Signup.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.Signup.name) {
            SignUpScreen(onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name) {
                    popUpTo(LoginRoutes.Signup.name) {
                        inclusive = true
                    }
                }
            }) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }

        composable(route = HomeRoutes.Home.name){
            Home()
        }
    }
}