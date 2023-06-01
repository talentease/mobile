package com.bangkit.c23pr492.talentease.ui.navigation

import com.bangkit.c23pr492.talentease.utils.Const.navKeyToken

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home/{$navKeyToken}") {
        fun createRoute(token: String) = "home/$token"
    }
}