package com.bangkit.c23pr492.talentease.ui.recruiter.other

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.data.model.profile.CreateProfileModel
import com.bangkit.c23pr492.talentease.ui.AuthViewModel
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.ui.component.LoadingProgressBar
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.AuthViewModelFactory
import com.bangkit.c23pr492.talentease.utils.RecruiterViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OtherScreen(
    token: String,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(context)
    ),
    otherViewModel: OtherViewModel = viewModel(
        factory = RecruiterViewModelFactory.getInstance(context)
    ),
    navigateToLogin: (String) -> Unit,
) {
    var isLoading by mutableStateOf(false)
    LoadingProgressBar(isLoading = isLoading)
    LaunchedEffect(key1 = true) {
        authViewModel.prepareEvent()
        authViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.Loading -> isLoading = true
                is UiEvents.NavigateEvent -> {
                    isLoading = false
                    navigateToLogin(event.route)
                }
                is UiEvents.SnackBarEvent -> {
                    isLoading = false
                }
            }
        }
    }
    val profileState = otherViewModel.profileState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    authViewModel.logout()
                }
            ) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout")
            }
        },
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            profileState.value.let { state ->
                when (state) {
                    UiState.Empty -> {
                        isLoading = false
                        ProfileContentScreen(
                            token = token,
                            text = "Create Profile",
                            otherViewModel = otherViewModel
                        )
                    }
                    is UiState.Error -> isLoading = false
                    UiState.Initial -> {
                        otherViewModel.getProfile(token)
                        isLoading = false
                    }
                    UiState.Loading -> isLoading = true
                    is UiState.Success -> {
                        isLoading = false
                        ProfileContentScreen(
                            token = token,
                            text = if (otherViewModel.readOnly) "Edit Profile" else "Save Profile",
                            otherViewModel = otherViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileContentScreen(
    token: String,
    text: String,
    otherViewModel: OtherViewModel
) {
    otherViewModel.apply {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = {
                        updateFirstName(it)
                    },
                    label = {
                        Text(text = "First Name")
                    },
                    readOnly = readOnly,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "First Name")
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = {
                        updateLastName(it)
                    },
                    label = {
                        Text(text = "Last Name")
                    },
                    readOnly = readOnly,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Last Name")
                    },
                    singleLine = false,
                    modifier = Modifier.weight(1f)
                )
            }
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    updatePhone(it)
                },
                label = {
                    Text(text = "Phone Number")
                },
                readOnly = readOnly,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Phone Number")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    updateEmail(it)
                },
                label = {
                    Text(text = "Email Address")
                },
                readOnly = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email Address")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        if (readOnly) {
                            updateReadOnly(false)
                        } else {
                            updateProfile(token, CreateProfileModel(firstName, lastName, phone))
                            updateReadOnly(true)
                        }
                    },
                    modifier = Modifier.weight(72f)
                ) {
                    if (readOnly) {
                        Text(text = text)
                    } else {
                        Text(text = text)
                    }
                }
                if (!readOnly) {
                    Button(
                        onClick = {
                            updateReadOnly(true)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        modifier = Modifier.weight(21f)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                    }
                }
            }
        }
    }
}