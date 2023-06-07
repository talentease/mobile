package com.bangkit.c23pr492.talentease.ui.position

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.data.model.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.component.EmptyContentScreen
import com.bangkit.c23pr492.talentease.ui.component.LoadingProgressBar
import com.bangkit.c23pr492.talentease.ui.component.PositionItems
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.Const
import com.bangkit.c23pr492.talentease.utils.Const.tagRepository
import com.bangkit.c23pr492.talentease.utils.MainViewModelFactory
import com.bangkit.c23pr492.talentease.utils.UiText.Companion.asString
import kotlinx.coroutines.launch

@Composable
fun PositionScreen(
    token: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(context)
    ),
    positionViewModel: PositionViewModel = viewModel(
        factory = MainViewModelFactory.getInstance(context)
    ),
    navigateToDetail: (String) -> Unit,
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
            val listState = rememberLazyGridState()
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
                        PositionContentScreen(
                            listState,
                            state.data,
//                    navigateToDetail = navigateToDetail
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
                Log.d("position", "PositionScreen: $state")
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PositionContentScreen(
    listState: LazyGridState = rememberLazyGridState(),
    data: List<PositionItemModel>,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 2 }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = Modifier
                .padding(all = 8.dp)
                .testTag(Const.tagTestList)
        ) {
            items(data, key = { it.id }) { position ->
                PositionItems(
                    position,
                    modifier = modifier
                        .padding(all = 8.dp)
                        .animateItemPlacement(tween(durationMillis = 100)),
//                    navigateToDetail = navigateToDetail
                )
                Log.d(tagRepository, "PositionContentScreen: berhitung 123")
            }
            item { }
        }
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(elevation = 6.dp, shape = RoundedCornerShape(size = 24.dp))
            .clip(shape = RoundedCornerShape(size = 24.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null
        )
    }
}