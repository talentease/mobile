package com.bangkit.c23pr492.talentease.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.common.UiEvents
import com.bangkit.c23pr492.talentease.ui.component.LoadingProgressBar
import com.bangkit.c23pr492.talentease.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    token: String,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToLogin: (String) -> Unit,
) {
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        authViewModel.isLogin()
        authViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.Loading -> isLoading = true
                is UiEvents.NavigateEvent -> {
                    isLoading = false
                    navigateToLogin(event.route)
                }
                is UiEvents.SnackBarEvent -> {
                    isLoading = false
                }
            }
        }
    }
    LoadingProgressBar(isLoading = isLoading)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = token)
        Button(
            onClick = {
                authViewModel.logout()
            }
        ) {
            Text(text = "Logout")
        }
    }
}