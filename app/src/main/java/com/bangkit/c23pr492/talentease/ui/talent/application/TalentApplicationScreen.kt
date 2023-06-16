package com.bangkit.c23pr492.talentease.ui.talent.application

import android.content.Context
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
import com.bangkit.c23pr492.talentease.ui.component.*
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.Const
import com.bangkit.c23pr492.talentease.utils.TalentViewModelFactory
import com.bangkit.c23pr492.talentease.utils.UiText.Companion.asString
import kotlinx.coroutines.launch

@Composable
fun TalentApplicationScreen(
    token: String,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    talentApplicationViewModel: TalentApplicationViewModel = viewModel(
        factory = TalentViewModelFactory.getInstance(context)
    )
) {
    var isLoading by rememberSaveable { mutableStateOf(false) }
    val listDataState = talentApplicationViewModel.listALlApplicationState.collectAsState()
    LoadingProgressBar(isLoading = isLoading)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        SearchBarScreen(
            token,
            talentApplicationViewModel
        )
        Spacer(modifier = Modifier.padding(bottom = 8.dp))
        val listState = rememberLazyListState()
        listDataState.value.let { state ->
            when (state) {
                UiState.Initial -> {
                    isLoading = false
                    talentApplicationViewModel.getAllTalentApplication(token)
                }
                is UiState.Loading -> isLoading = true
                is UiState.Empty -> {
                    isLoading = false
                    EmptyContentScreen(R.string.empty_list, modifier)
                }
                is UiState.Success -> {
                    isLoading = false
                    TalentApplicationContentScreen(
                        token,
                        listState,
                        state.data,
//                        navigateToDetail = navigateToDetail
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(token: String, talentApplicationViewModel: TalentApplicationViewModel) {
    val query by talentApplicationViewModel.query.collectAsState()
    var active by rememberSaveable { mutableStateOf(false) }
    SearchBar(
        query = query,
        onQueryChange = {
            talentApplicationViewModel.searchPositions(token, it)
        },
        onSearch = {
            active = false
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(text = "Search your dream job")
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
                            talentApplicationViewModel.searchPositions(token, "")
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
fun TalentApplicationContentScreen(
    token: String,
    listState: LazyListState = rememberLazyListState(),
    data: List<DataItem>,
    modifier: Modifier = Modifier,
//    navigateToDetail: (String, String) -> Unit
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 1 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = modifier.testTag(Const.tagTestList)
        ) {
            items(data, key = { it.id }) { application ->
                TalentApplicationItems(
                    token,
                    application,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateItemPlacement(tween(durationMillis = 100)),
//                    navigateToDetail = navigateToDetail
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

@Composable
fun TalentApplicationItems(
    token: String,
    application: DataItem,
    modifier: Modifier = Modifier,
//    navigateToDetail: (String, String) -> Unit
) {
    Card(
        modifier = modifier,
//        onClick = {
//            navigateToDetail(token, vacancy.position.positionId)
//        }
    ) {
        application.apply {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                TitleText(
                    string = "${candidate.firstName} ${candidate.lastName}",
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                DescriptionText(
                    string = application.position.company.name,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                StatusAndPositionText(
                    status = application.status,
                    position = application.position.title,
                    modifier = Modifier.padding()
                )
            }
        }
    }
}