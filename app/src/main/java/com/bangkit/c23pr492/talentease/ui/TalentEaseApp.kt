package com.bangkit.c23pr492.talentease.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bangkit.c23pr492.talentease.ui.login.LoginScreen

@Composable
fun TalentEaseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    LoginScreen(modifier)
}