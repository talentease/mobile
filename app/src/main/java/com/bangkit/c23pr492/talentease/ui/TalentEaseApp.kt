package com.bangkit.c23pr492.talentease.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.ui.navigation.NavigationItem
import com.bangkit.c23pr492.talentease.ui.navigation.Screen
import com.bangkit.c23pr492.talentease.ui.navigation.authNavGraph
import com.bangkit.c23pr492.talentease.ui.navigation.mainNavGraph

@Composable
fun TalentEaseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var token by rememberSaveable { mutableStateOf("") }
    val strings: MutableSet<String> = HashSet()
    strings.add(Screen.Application.route)
    strings.add(Screen.Position.route)
    strings.add(Screen.Other.route)
    Scaffold(
        bottomBar = {
            if (strings.contains(currentRoute)) {
                BottomBar(navController, token)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = modifier.padding(it)
        ) {
            composable(route = Screen.Splash.route) {
                SplashScreen(
                    navigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Splash.route) {
                                inclusive = true
                            }
                        }
                    },
                    token = { value ->
                        token = value
                    }
                )
            }
            authNavGraph(navController)
            mainNavGraph(navController)
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    token: String,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(id = R.string.application),
            icon = Icons.Default.Person,
            screen = Screen.Application,
            routeWithToken = Screen.Application.createRoute(token),
            contentDesc = stringResource(R.string.application)
        ),
        NavigationItem(
            title = stringResource(R.string.position),
            icon = Icons.Default.Groups,
            screen = Screen.Position,
            routeWithToken = Screen.Position.createRoute(token),
            contentDesc = stringResource(R.string.position_page)
        ),
        NavigationItem(
            title = stringResource(R.string.other),
            icon = Icons.Default.AccountCircle,
            screen = Screen.Other,
            routeWithToken = Screen.Other.createRoute(token),
            contentDesc = stringResource(R.string.other_page)
        ),
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDesc
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.routeWithToken) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}