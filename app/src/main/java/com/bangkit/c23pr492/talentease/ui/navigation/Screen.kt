package com.bangkit.c23pr492.talentease.ui.navigation

import com.bangkit.c23pr492.talentease.utils.Const.navKeyApplication
import com.bangkit.c23pr492.talentease.utils.Const.navKeyPosition
import com.bangkit.c23pr492.talentease.utils.Const.navKeyToken

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    // Recruiter
    object Application : Screen("recruiter/application/{$navKeyToken}") {
        fun createRoute(token: String) = "recruiter/application/$token"
    }
    object Position : Screen("recruiter/position/{$navKeyToken}") {
        fun createRoute(token: String) = "recruiter/position/$token"
    }
    object Other : Screen("recruiter/other/{$navKeyToken}") {
        fun createRoute(token: String) = "recruiter/other/$token"
    }
    object DetailRecruiterApplication : Screen("recruiter/application/detail/{$navKeyToken}&{$navKeyApplication}") {
        fun createRoute(token: String, applicationId: String) = "recruiter/application/detail/$token&$applicationId"
    }
    object DetailPosition : Screen("recruiter/position/detail/{$navKeyToken}&{$navKeyPosition}") {
        fun createRoute(token: String, positionId: String) = "recruiter/position/detail/$token&$positionId"
    }
    object AddPosition : Screen("recruiter/position/add/{$navKeyToken}") {
        fun createRoute(token: String) = "recruiter/position/add/$token"
    }
    // Talent
    object Vacancy : Screen("talent/vacancy/{$navKeyToken}") {
        fun createRoute(token: String) = "talent/vacancy/$token"
    }
    object TalentApplication : Screen("talent/application/{$navKeyToken}") {
        fun createRoute(token: String) = "talent/application/$token"
    }
    object Profile : Screen("talent/profile/{$navKeyToken}") {
        fun createRoute(token: String) = "talent/profile/$token"
    }
    object DetailVacancy : Screen("talent/vacancy/detail/{$navKeyToken}&{$navKeyPosition}") {
        fun createRoute(token: String, positionId: String) = "talent/vacancy/detail/$token&$positionId"
    }
}