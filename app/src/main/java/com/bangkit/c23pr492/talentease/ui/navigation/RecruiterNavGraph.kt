package com.bangkit.c23pr492.talentease.ui.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bangkit.c23pr492.talentease.ui.recruiter.application.ApplicationScreen
import com.bangkit.c23pr492.talentease.ui.recruiter.application.detail.DetailRecruiterApplicationScreen
import com.bangkit.c23pr492.talentease.ui.recruiter.other.OtherScreen
import com.bangkit.c23pr492.talentease.ui.recruiter.position.PositionScreen
import com.bangkit.c23pr492.talentease.ui.recruiter.position.add.AddPositionScreen
import com.bangkit.c23pr492.talentease.ui.recruiter.position.detail.DetailPositionScreen
import com.bangkit.c23pr492.talentease.utils.Const.navKeyPosition
import com.bangkit.c23pr492.talentease.utils.Const.navKeyToken
import com.bangkit.c23pr492.talentease.utils.Const.recruiterGraphRoute

fun NavGraphBuilder.recruiterNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Application.route,
        route = recruiterGraphRoute
    ) {
        composable(
            route = Screen.Application.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            ApplicationScreen(
                token = token,
                navigateToDetail = { navToken ->
                    navController.navigate(Screen.DetailRecruiterApplication.createRoute(navToken))
                }
            )
        }
        composable(
            route = Screen.Position.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            PositionScreen(
                token = token,
                navigateToDetail = { navToken, navPosition ->
                    Log.d("position", "recruiterNavGraph: ${Screen.DetailPosition.createRoute(navToken, navPosition)}")
                    navController.navigate(Screen.DetailPosition.createRoute(navToken, navPosition))
                },
                navigateToAdd = { navToken ->
                    navController.navigate(Screen.AddPosition.createRoute(navToken))
                }
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
                        popUpTo(recruiterGraphRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = Screen.DetailRecruiterApplication.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            DetailRecruiterApplicationScreen(
                token = token,
            )
        }
        composable(
            route = Screen.DetailPosition.route,
            arguments = listOf(
                navArgument(navKeyToken) { type = NavType.StringType },
                navArgument(navKeyPosition) { type = NavType.StringType },
            ),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            val positionId = it.arguments?.getString(navKeyPosition) ?: ""
            Log.d("position", "recruiterNavGraph: $positionId")
            DetailPositionScreen(
                token = token,
                positionId = positionId
            )
        }
        composable(
            route = Screen.AddPosition.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            AddPositionScreen(
                token = token,
                navigateToPosition = { route ->
                    navController.navigate(route) {
                        popUpTo(recruiterGraphRoute) {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
