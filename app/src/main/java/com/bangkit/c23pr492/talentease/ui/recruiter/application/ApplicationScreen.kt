package com.bangkit.c23pr492.talentease.ui.recruiter.application

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.bangkit.c23pr492.talentease.data.model.application.DataItem
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.component.*
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.Const.tagTestList
import com.bangkit.c23pr492.talentease.utils.RecruiterViewModelFactory
import com.bangkit.c23pr492.talentease.utils.UiText.Companion.asString
import kotlinx.coroutines.launch

@Composable
fun ApplicationScreen(
    token: String,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(context)
    ),
    applicationViewModel: ApplicationViewModel = viewModel(
        factory = RecruiterViewModelFactory.getInstance(context)
    ),
    navigateToDetail: (String, String) -> Unit
) {
    val listApplicationState = applicationViewModel.listApplicationState.collectAsState()

    LaunchedEffect(key1 = true) {
        applicationViewModel.getAllPositions(token)
    }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LoadingProgressBar(isLoading = isLoading)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        SearchBarScreen(applicationViewModel = applicationViewModel)
        val listState = rememberLazyListState()
        listApplicationState.value.let { position ->
            when (position) {
                UiState.Empty -> {
                    isLoading = false
                    EmptyContentScreen(R.string.empty_list, modifier)
                }
                UiState.Initial -> {
                    isLoading = false
                }
                UiState.Loading -> isLoading = true
                is UiState.Error -> {
                    isLoading = false
                    Toast.makeText(
                        context,
                        position.error.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiState.Success -> {
                    isLoading = false
                    Log.d("kenapa", "ApplicationScreen: ${position.data}")
                    ApplicationContentScreen(
                        token = token,
                        listState = listState,
                        data = position.data,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }
    }
//    Log.d("token", "ApplicationScreen: $token")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(applicationViewModel: ApplicationViewModel) {
    val query by applicationViewModel.query.collectAsState()
    var active by rememberSaveable { mutableStateOf(false) }
    SearchBar(
        query = query,
        onQueryChange = applicationViewModel::searchApplications,
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
                            applicationViewModel.searchApplications("")
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
fun ApplicationContentScreen(
    token: String,
    listState: LazyListState = rememberLazyListState(),
    data: MutableList<DataItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String, String) -> Unit
) {
    Log.d("apprepo", "ApplicationContentScreen: final $data")
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 1 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = modifier
                .testTag(tagTestList)
                .padding(all = 8.dp)
        ) {
            items(data, key = { it.id }) { application ->
                ApplicationItems(
                    token,
                    application,
                    modifier = modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                        .animateItemPlacement(tween(durationMillis = 100)),
                    navigateToDetail = navigateToDetail
                )
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
fun ApplicationItems(
    token: String,
    application: DataItem,
    modifier: Modifier = Modifier,
    navigateToDetail: (String, String) -> Unit,
) {
    application.apply {
        Card(
            modifier = modifier.clickable { navigateToDetail(token, positionId) }
        ) {
            Column(modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()) {
                TitleText(
                    string = "${candidate.firstName}  ${candidate.lastName}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                DescriptionText(
                    string = position.title,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                RegularText(
                    string = status,
                    modifier = Modifier.padding()
                )
            }
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