package com.bangkit.c23pr492.talentease.ui.recruiter.application

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.c23pr492.talentease.data.model.ApplicationModel
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.values.spacingRegular
import com.bangkit.c23pr492.talentease.ui.values.textLarge
import com.bangkit.c23pr492.talentease.ui.values.textRegular
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.Const.tagTestList
import com.bangkit.c23pr492.talentease.utils.RecruiterViewModelFactory
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
    navigateToDetail: (String) -> Unit
) {
//    val listDataState = applicationViewModel.listApplicationState.collectAsState()
//    val listPositionState = applicationViewModel.listPositionState.collectAsState()
//    var isLoading by rememberSaveable { mutableStateOf(false) }
//    LoadingProgressBar(isLoading = isLoading)
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = modifier.fillMaxSize()
//    ) {
////        SearchBarScreen(applicationViewModel)
//        val listState = rememberLazyListState()
//        listPositionState.value.let { state ->
//            when (state) {
//                UiState.Empty -> {
//                    isLoading = false
//                    EmptyContentScreen(R.string.empty_list, modifier)
//                }
//                is UiState.Error -> {
//                    isLoading = false
//                    Toast.makeText(
//                        LocalContext.current,
//                        state.error.asString(LocalContext.current),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                UiState.Initial -> {
//                    isLoading = false
//                    applicationViewModel.getAllPositions(token)
//                }
//                UiState.Loading -> isLoading = true
//                is UiState.Success -> {
//                    state.data.forEach {
//                        applicationViewModel.getAllApplications(token, it.id)
//                    }
//                }
//            }
//        }
//        listDataState.value.let { state ->
//            when (state) {
//                UiState.Initial -> {
//                    isLoading = false
//                    applicationViewModel.getAllApplications(token, )
//                }
//                is UiState.Loading -> isLoading = true
//                is UiState.Empty -> {
//                    isLoading = false
//                    EmptyContentScreen(R.string.empty_list, modifier)
//                }
//                is UiState.Success -> {
//                    isLoading = false
//                    ApplicationContentScreen(
//                        token,
//                        listState,
//                        state.data,
//                        navigateToDetail = navigateToDetail
//                    )
//                }
//                is UiState.Error -> {
//                    isLoading = false
//                    Toast.makeText(
//                        LocalContext.current,
//                        state.error.asString(LocalContext.current),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
//    Log.d("token", "ApplicationScreen: $token")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(applicationViewModel: ApplicationViewModel) {
//    val query by applicationViewModel.query.collectAsState()
//    var active by rememberSaveable { mutableStateOf(false) }
//    SearchBar(
//        query = query,
//        onQueryChange = applicationViewModel::searchApplications,
//        onSearch = {
//            active = false
//        },
//        active = active,
//        onActiveChange = {
//            active = it
//        },
//        placeholder = {
//            Text(text = "Search talent's name")
//        },
//        leadingIcon = {
//            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
//        },
//        trailingIcon = {
//            if (active) {
//                Icon(
//                    imageVector = Icons.Default.Close,
//                    contentDescription = "Search Icon",
//                    modifier = Modifier.clickable {
//                        if (query.isNotEmpty()) {
//                            applicationViewModel.searchApplications("")
//                        } else {
//                            active = false
//                        }
//                    }
//                )
//            }
//        }
//    ) {
//
//    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ApplicationContentScreen(
    token: String,
    listState: LazyListState = rememberLazyListState(),
    data: List<ApplicationModel>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 1 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = modifier.testTag(tagTestList)
        ) {
            items(data, key = { it.id }) { application ->
                ApplicationItems(
                    token,
                    application,
                    modifier = modifier
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ApplicationItems(
    token: String,
    application: ApplicationModel,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    application.apply {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .combinedClickable(
                    onClick = {

                    },
                    onLongClick = {

                    }
                )
                .clickable {
                    navigateToDetail(token)
                }
                .padding(spacingRegular)
        ) {
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(end = spacingRegular)
                    .size(54.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = textLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "$status / $position",
                    fontWeight = FontWeight.Normal,
                    fontSize = textRegular,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
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