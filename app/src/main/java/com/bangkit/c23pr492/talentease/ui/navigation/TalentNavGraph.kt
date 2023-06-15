package com.bangkit.c23pr492.talentease.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bangkit.c23pr492.talentease.ui.talent.application.TalentApplicationScreen
import com.bangkit.c23pr492.talentease.ui.talent.profile.ProfileScreen
import com.bangkit.c23pr492.talentease.ui.talent.vacancy.VacancyScreen
import com.bangkit.c23pr492.talentease.ui.talent.vacancy.detail.DetailVacancyScreen
import com.bangkit.c23pr492.talentease.utils.Const.navKeyPosition
import com.bangkit.c23pr492.talentease.utils.Const.navKeyToken
import com.bangkit.c23pr492.talentease.utils.Const.talentGraphRoute

fun NavGraphBuilder.talentNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Vacancy.route,
        route = talentGraphRoute
    ) {
        composable(
            route = Screen.Vacancy.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            VacancyScreen(
                token = token,
                navigateToDetail = { navToken, positionId ->
                    navController.navigate(Screen.DetailVacancy.createRoute(navToken, positionId))
                }
            )
        }
        composable(
            route = Screen.TalentApplication.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            TalentApplicationScreen(
                token = token,
            )
        }
        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument(navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            ProfileScreen(
                token = token,
                navigateToLogin = { route ->
                    navController.navigate(route) {
                        popUpTo(talentGraphRoute) {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(
            route = Screen.DetailVacancy.route,
            arguments = listOf(
                navArgument(navKeyToken) { type = NavType.StringType },
                navArgument(navKeyPosition) { type = NavType.StringType }
            ),
        ) {
            val token = it.arguments?.getString(navKeyToken) ?: ""
            val positionId = it.arguments?.getString(navKeyPosition) ?: ""
            DetailVacancyScreen(
                token = token,
                positionId = positionId,
                navigateToApplication = { navToken ->
                    navController.navigate(Screen.TalentApplication.createRoute(navToken)) {
                        popUpTo(talentGraphRoute) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}