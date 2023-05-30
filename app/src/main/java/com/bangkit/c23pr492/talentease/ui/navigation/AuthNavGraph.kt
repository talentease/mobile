package com.bangkit.c23pr492.talentease.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bangkit.c23pr492.talentease.ui.login.LoginScreen
import com.bangkit.c23pr492.talentease.ui.register.RegisterScreen
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
                navigateToHome = { navToken ->
                    navController.popBackStack()
                    navController.navigate(Screen.Home.createRoute(navToken))
                },
                navigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterScreen()
        }
    }
}