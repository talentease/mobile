package com.bangkit.c23pr492.talentease.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen,
    val routeWithToken: String,
    val contentDesc: String
)