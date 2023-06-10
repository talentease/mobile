package com.bangkit.c23pr492.talentease.ui.navigation

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
    object DetailApplication : Screen("recruiter/application/detail/{$navKeyToken}") {
        fun createRoute(token: String) = "recruiter/application/detail/$token"
    }
    object DetailPosition : Screen("recruiter/position/detail/{$navKeyToken}") {
        fun createRoute(token: String) = "recruiter/position/detail/$token"
    }
    object AddPosition : Screen("recruiter/position/add/{$navKeyToken}") {
        fun createRoute(token: String) = "recruiter/position/add/$token"
    }
    // Talent
    object Vacancy : Screen("talent/vacancy/{$navKeyToken}") {
        fun createRoute(token: String) = "talent/vacancy/$token"
    }
    object TalentApplication : Screen("talent/application/{$navKeyToken}") {
        fun createRoute(token: String) = "position/$token"
    }
    object Profile : Screen("talent/profile/{$navKeyToken}") {
        fun createRoute(token: String) = "talent/profile/$token"
    }
}