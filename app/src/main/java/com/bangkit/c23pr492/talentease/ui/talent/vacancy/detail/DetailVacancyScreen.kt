package com.bangkit.c23pr492.talentease.ui.talent.vacancy.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.component.EmptyContentScreen
import com.bangkit.c23pr492.talentease.ui.component.LoadingProgressBar
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.TalentViewModelFactory
import com.bangkit.c23pr492.talentease.utils.UiText.Companion.asString

@Composable
fun DetailVacancyScreen(
    token: String,
    positionId: String,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(context)
    ),
    detailVacancyViewModel: DetailVacancyViewModel = viewModel(
        factory = TalentViewModelFactory.getInstance(context)
    ),
    navigateToApplication: (String) -> Unit
) {
    val dataState = detailVacancyViewModel.positionState.collectAsState()
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LoadingProgressBar(isLoading = isLoading, modifier = modifier)
    dataState.value.let { state ->
        when (state) {
            UiState.Initial -> {
                isLoading = false
                detailVacancyViewModel.getPositionByPositionId(token, positionId)
            }
            is UiState.Loading -> isLoading = true
            is UiState.Empty -> {
                isLoading = false
                EmptyContentScreen(R.string.empty_list, modifier)
            }
            is UiState.Success -> {
                isLoading = false
                DetailVacancyContentScreen(
                    token,
                    state.data,
                    detailVacancyViewModel,
                    navigateToApplication
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

@Composable
fun DetailVacancyContentScreen(
    token: String,
    position: PositionItemModel,
    detailVacancyViewModel: DetailVacancyViewModel,
    navigateToApplication: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Card {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = position.title ?: "")
                Text(text = position.company?.name ?: "")
                Text(text = position.company?.address ?: "")
            }
        }
        Card {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = position.type ?: "")
                Text(text = position.description ?: "")
            }
        }
        Button(
            onClick = {
                detailVacancyViewModel.applyApplication(token, position.id, null)
                navigateToApplication(token)
            }
        ) {
            Text(text = "Apply Now")
        }
    }
}