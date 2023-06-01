package com.bangkit.c23pr492.talentease.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bangkit.c23pr492.talentease.ui.navigation.Screen
import com.bangkit.c23pr492.talentease.ui.navigation.authNavGraph
import com.bangkit.c23pr492.talentease.ui.navigation.mainNavGraph

@Composable
fun TalentEaseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                navigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}