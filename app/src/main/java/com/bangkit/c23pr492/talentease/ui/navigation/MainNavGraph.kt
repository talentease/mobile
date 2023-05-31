package com.bangkit.c23pr492.talentease.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bangkit.c23pr492.talentease.ui.home.HomeScreen
import com.bangkit.c23pr492.talentease.utils.Const.mainGraphRoute
import com.bangkit.c23pr492.talentease.utils.Const.navKeyToken

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Home.route,
        route = mainGraphRoute
    ) {
        composable(
            route = Screen.Home.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            HomeScreen(
                token = token,
                navigateToLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                }
            )
        }
    }
}
