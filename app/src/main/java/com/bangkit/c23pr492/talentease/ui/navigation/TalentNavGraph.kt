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
import com.bangkit.c23pr492.talentease.utils.Const

fun NavGraphBuilder.talentNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.Vacancy.route,
        route = Const.talentGraphRoute
    ) {
        composable(
            route = Screen.Vacancy.route,
            arguments = listOf(navArgument(Const.navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(Const.navKeyToken) ?: ""
            VacancyScreen(
                token = token,
            )
        }
        composable(
            route = Screen.TalentApplication.route,
            arguments = listOf(navArgument(Const.navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(Const.navKeyToken) ?: ""
            TalentApplicationScreen(
                token = token,
            )
        }
        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument(Const.navKeyToken) { type = NavType.StringType }),
        ) {
            val token = it.arguments?.getString(Const.navKeyToken) ?: ""
            ProfileScreen(
                token = token,
            )
        }
    }
}