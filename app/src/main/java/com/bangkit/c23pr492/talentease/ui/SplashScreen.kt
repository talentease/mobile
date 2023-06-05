package com.bangkit.c23pr492.talentease.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.utils.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigate: (String) -> Unit,
    authViewModel: AuthViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    token: (String) -> Unit
) {
    Text(text = "SplashScreen")
    LaunchedEffect(key1 = true) {
        authViewModel.apply {
            isLogin()
            getToken()
            eventFlow.collectLatest { event ->
                when (event) {
                    is UiEvents.Loading -> {}
                    is UiEvents.NavigateEvent -> {
                        navigate(event.route)
                        token(event.route.substringAfter("/"))
                    }
                    is UiEvents.SnackBarEvent -> {}
                }
            }
        }
    }
}