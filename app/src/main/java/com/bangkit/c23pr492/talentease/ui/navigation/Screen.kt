package com.bangkit.c23pr492.talentease.ui.navigation

import com.bangkit.c23pr492.talentease.utils.Const.navKeyId

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Detail : Screen("home/{$navKeyId}") {
        fun createRoute(navKeyId: Int) = "home/$navKeyId"
    }
}