package com.bangkit.c23pr492.talentease.ui.recruiter.position

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.component.EmptyContentScreen
import com.bangkit.c23pr492.talentease.ui.component.LoadingProgressBar
import com.bangkit.c23pr492.talentease.ui.component.PositionContentScreen
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.RecruiterViewModelFactory
import com.bangkit.c23pr492.talentease.utils.UiText.Companion.asString

@Composable
fun PositionScreen(
    token: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(context)
    ),
    positionViewModel: PositionViewModel = viewModel(
        factory = RecruiterViewModelFactory.getInstance()
    ),
    navigateToDetail: (String, String) -> Unit,
    navigateToAdd: (String) -> Unit,
) {
    val listDataState = positionViewModel.listPositionState.collectAsState()
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LoadingProgressBar(isLoading = isLoading)
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = { navigateToAdd(token) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add a new position")
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            SearchBarScreen(token, positionViewModel)
            val listState = rememberLazyListState()
            listDataState.value.let { state ->
                when (state) {
                    UiState.Initial -> {
                        isLoading = false
                        positionViewModel.getAllPositions(token)
                    }
                    is UiState.Loading -> isLoading = true
                    is UiState.Empty -> {
                        isLoading = false
                        EmptyContentScreen(R.string.empty_list, modifier)
                    }
                    is UiState.Success -> {
                        isLoading = false
                        Log.d("position", "PositionScreen: ${state.data}")
                        PositionContentScreen(
                            token,
                            listState,
                            state.data,
                            navigateToDetail = navigateToDetail
                        )
                    }
                    is UiState.Error -> {
                        isLoading = false
                        Toast.makeText(
                            LocalContext.current,
                            state.error.asString(LocalContext.current),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    Log.d("token", "PositionScreen: $token")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(token: String, positionViewModel: PositionViewModel) {
    val query by positionViewModel.query.collectAsState()
    var active by rememberSaveable { mutableStateOf(false) }
    SearchBar(
        query = query,
        onQueryChange = {
            positionViewModel.searchPositions(token = token, newQuery = it)
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Search talent's name")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        trailingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Search Icon",
                    modifier = Modifier.clickable {
                        if (query.isNotEmpty()) {
                            positionViewModel.searchPositions(token = token, newQuery = "")
                        } else {
                            active = false
                        }
                    }
                )
            }
        }
    ) {

    }
}