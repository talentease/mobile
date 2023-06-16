package com.bangkit.c23pr492.talentease.ui.talent.vacancy.detail

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.File.uriToFile
import com.bangkit.c23pr492.talentease.utils.TalentViewModelFactory
import com.bangkit.c23pr492.talentease.utils.UiText.Companion.asString
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

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
    LoadingProgressBar(isLoading = isLoading)
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
    val context = LocalContext.current
    var cvMultipart: MultipartBody.Part? = null
    val pickFileLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedFile: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedFile, context)
            val requestPdfFile = myFile.asRequestBody("application/pdf".toMediaTypeOrNull())
            Log.d("upload", "DetailVacancyContentScreen: req $requestPdfFile")
            Log.d("upload", "DetailVacancyContentScreen: req ${myFile.name}")
            cvMultipart = MultipartBody.Part.createFormData(
                "cv",
                myFile.name,
                requestPdfFile
            )
            Log.d("upload", "DetailVacancyContentScreen: imp $cvMultipart")
        }
    }
    LaunchedEffect(key1 = true) {
        detailVacancyViewModel.eventFlow.collectLatest {
            when (it) {
                is UiEvents.NavigateEvent -> navigateToApplication(it.route)
                else -> {}
            }
        }
    }
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
                val intent = Intent()
                intent.action = Intent.ACTION_GET_CONTENT
                intent.type = "application/pdf"
                val chooser = Intent.createChooser(intent, "Choose a Pdf")
                pickFileLauncher.launch(chooser)
            }
        ) {
            Text(text = "Select CV")
        }
        Button(
            onClick = {
                cvMultipart?.let { detailVacancyViewModel.applyPosition(token, position.id, it) }
            }
        ) {
            Text(text = "Apply Now")
        }
    }
}