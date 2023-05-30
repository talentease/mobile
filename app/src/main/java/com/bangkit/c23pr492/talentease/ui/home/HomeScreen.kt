package com.bangkit.c23pr492.talentease.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.ui.common.UiState
import com.bangkit.c23pr492.talentease.utils.ViewModelFactory

@Composable
fun HomeScreen(
    token: String,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToLogin: () -> Unit,
) {
    val authDataState = homeViewModel.tokenState.collectAsState()
    authDataState.value.let {
        when(it){
            UiState.Empty -> {}
            is UiState.Error -> Log.d("home", "HomeScreen: Error")
            UiState.Initial -> {}
            UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> navigateToLogin()
        }
    }
    Column(modifier = Modifier) {
        Text(text = token)
        Button(
            onClick = {
                homeViewModel.logout()
            }
        ) {
            Text(text = "Logout")
        }
    }
}