package com.bangkit.c23pr492.talentease.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bangkit.c23pr492.talentease.ui.navigation.authNavGraph
import com.bangkit.c23pr492.talentease.ui.navigation.mainNavGraph
import com.bangkit.c23pr492.talentease.utils.Const.authGraphRoute

@Composable
fun TalentEaseApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = authGraphRoute,
        modifier = Modifier
    ) {
        authNavGraph(navController)
        mainNavGraph(navController)
    }
}

//@Composable
//inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
//    val navGraphRoute = destination.parent?.route ?: return viewModel()
//    val parentEntry = remember(this) {
//        navController.getBackStackEntry(navGraphRoute)
//    }
//    return viewModel(parentEntry)
//}