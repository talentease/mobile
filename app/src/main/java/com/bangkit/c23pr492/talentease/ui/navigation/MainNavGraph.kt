package com.bangkit.c23pr492.talentease.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bangkit.c23pr492.talentease.ui.application.ApplicationScreen
import com.bangkit.c23pr492.talentease.ui.other.OtherScreen
import com.bangkit.c23pr492.talentease.ui.position.PositionScreen
import com.bangkit.c23pr492.talentease.utils.Const.mainGraphRoute
import com.bangkit.c23pr492.talentease.utils.Const.navKeyToken

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Application.route,
        route = mainGraphRoute
    ) {
        composable(
            route = Screen.Application.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            ApplicationScreen(
                token = token,
            )
        }
        composable(
            route = Screen.Position.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            PositionScreen(
                token = token,
            )
        }
        composable(
            route = Screen.Other.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            OtherScreen(
                token = token,
                navigateToLogin = { route ->
                    navController.navigate(route) {
                        popUpTo(mainGraphRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
