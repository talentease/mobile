package com.bangkit.c23pr492.talentease.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.bangkit.c23pr492.talentease.ui.navigation.*

@Composable
fun TalentEaseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var token by rememberSaveable { mutableStateOf("") }
    var role by rememberSaveable { mutableStateOf("") }
    val strings: Set<String> = hashSetOf(
        Screen.Application.route,
        Screen.Position.route,
        Screen.Other.route,
        Screen.Vacancy.route,
        Screen.TalentApplication.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (strings.contains(currentRoute)) {
                BottomBar(navController, token, role)
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
                    },
                    role = { value ->
                        role = value
                    }
                )
            }
            authNavGraph(navController)
            recruiterNavGraph(navController, role = { value ->
                role = value
            })
            talentNavGraph(navController)
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    token: String,
    role: String
) {
    var navigationItems: List<NavigationItem> = emptyList()
    when (role) {
        "recruiter" -> {
            navigationItems = listOf(
                NavigationItem(
                    title = stringResource(R.string.application),
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
                )
            )
        }
        "talent" -> {
            navigationItems = listOf(
                NavigationItem(
                    title = stringResource(R.string.vacancy),
                    icon = Icons.Default.Dashboard,
                    screen = Screen.Vacancy,
                    routeWithToken = Screen.Vacancy.createRoute(token),
                    contentDesc = stringResource(R.string.vacancy)
                ),
                NavigationItem(
                    title = stringResource(R.string.application),
                    icon = Icons.Default.ListAlt,
                    screen = Screen.TalentApplication,
                    routeWithToken = Screen.TalentApplication.createRoute(token),
                    contentDesc = stringResource(R.string.application)
                ),
                NavigationItem(
                    title = stringResource(R.string.profile),
                    icon = Icons.Default.AccountCircle,
                    screen = Screen.Profile,
                    routeWithToken = Screen.Profile.createRoute(token),
                    contentDesc = stringResource(R.string.profile)
                )
            )
        }
    }

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