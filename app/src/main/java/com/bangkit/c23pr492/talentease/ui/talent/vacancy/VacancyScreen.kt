package com.bangkit.c23pr492.talentease.ui.talent.vacancy

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.component.LoadingProgressBar
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.TalentViewModelFactory

@Composable
fun VacancyScreen(
    token: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(context)
    ),
    vacancyViewModel: VacancyViewModel = viewModel(
        factory = TalentViewModelFactory.getInstance(context)
    ),
//    navigateToDetail: (String) -> Unit
) {
//    val listDataState = vacancyViewModel.listPositionState.collectAsState()
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LoadingProgressBar(isLoading = isLoading)
}