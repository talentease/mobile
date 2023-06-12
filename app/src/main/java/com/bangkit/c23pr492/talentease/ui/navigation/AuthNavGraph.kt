package com.bangkit.c23pr492.talentease.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bangkit.c23pr492.talentease.ui.auth.login.LoginScreen
import com.bangkit.c23pr492.talentease.ui.auth.register.RegisterScreen
import com.bangkit.c23pr492.talentease.utils.Const.authGraphRoute

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Screen.Login.route,
        route = authGraphRoute
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(
                navigateToHome = { route ->
                    navController.navigate(route) {
                        popUpTo(authGraphRoute) {
                            inclusive = true
                        }
                    }
                },
                navigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterScreen(
                navigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                navigateToLoginAlreadyRegister = { route ->
                    navController.navigate(route) {
                        popUpTo(authGraphRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}