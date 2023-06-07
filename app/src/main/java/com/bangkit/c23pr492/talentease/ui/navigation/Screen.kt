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
    object DetailApplication : Screen("application/detail/{$navKeyToken}") {
        fun createRoute(token: String) = "application/detail/$token"
    }
    object DetailPosition : Screen("position/detail/{$navKeyToken}") {
        fun createRoute(token: String) = "position/detail/$token"
    }
    object AddPosition : Screen("position/add/{$navKeyToken}") {
        fun createRoute(token: String) = "position/add/$token"
    }
}