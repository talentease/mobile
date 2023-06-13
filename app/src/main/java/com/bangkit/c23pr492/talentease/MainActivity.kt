package com.bangkit.c23pr492.talentease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.TalentEaseApp
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.ui.theme.TalentEaseTheme
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TalentEase)
        val factory: AuthViewModelFactory = AuthViewModelFactory.getInstance(this)
        val authViewModel: AuthViewModel by viewModels {
            factory
        }
        lifecycleScope.launch(Dispatchers.IO) {
            authViewModel.tokenState.collectLatest {
                when (it) {
                    UiState.Empty -> {

                    }
                    is UiState.Error -> {

                    }
                    UiState.Initial -> {

                    }
                    UiState.Loading -> {

                    }
                    is UiState.Success -> {

                    }
                }
            }
        }
        setContent {
            TalentEaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LaunchedEffect(key1 = true) {

                    }
                    lifecycleScope.launch(Dispatchers.IO) {
                        authViewModel.tokenState.collectLatest {
                            when (it) {
                                UiState.Empty -> {

                                }
                                is UiState.Error -> {

                                }
                                UiState.Initial -> {

                                }
                                UiState.Loading -> {

                                }
                                is UiState.Success -> {

                                }
                            }
                        }
                    }
                    TalentEaseApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TalentEaseTheme {

    }
}