package com.bangkit.c23pr492.talentease.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navigate: (String) -> Unit,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(LocalContext.current)
    ),
    token: (String) -> Unit,
    role: (String) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        authViewModel.apply {
            getToken()
            getRole()
            prepareEvent()
            eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvents.SnackBarEvent -> {}
                    is UiEvents.Loading -> {}
                    is UiEvents.NavigateEvent -> {
                        token(event.route.substringAfterLast("/"))
                        role(event.route.substringBefore("/"))
                        navigate(event.route)
                    }
                }
            }
        }
    }
}