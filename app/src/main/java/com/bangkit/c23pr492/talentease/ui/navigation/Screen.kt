package com.bangkit.c23pr492.talentease.ui.navigation

import com.bangkit.c23pr492.talentease.utils.Const.navKeyToken

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Application : Screen("application/{$navKeyToken}") {
        fun createRoute(token: String) = "application/$token"
    }
    object Position : Screen("position/{$navKeyToken}") {
        fun createRoute(token: String) = "position/$token"
    }
    object Other : Screen("other/{$navKeyToken}") {
        fun createRoute(token: String) = "other/$token"
    }
}