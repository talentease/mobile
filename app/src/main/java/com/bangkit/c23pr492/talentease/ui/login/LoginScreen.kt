package com.bangkit.c23pr492.talentease.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.ui.common.UiState
import com.bangkit.c23pr492.talentease.ui.theme.TalentEaseTheme
import com.bangkit.c23pr492.talentease.utils.ViewModelFactory
import com.bangkit.c23pr492.talentease.utils.autofill

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val authDataState = loginViewModel.loginState.collectAsState()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        var textEmail by rememberSaveable { mutableStateOf("") }
        var textPassword by rememberSaveable { mutableStateOf("") }
        Image(
            painter = painterResource(id = R.drawable.ic_talentease),
            contentDescription = null,
        )
        OutlinedTextField(
            value = textEmail,
            onValueChange = {
                textEmail = it
            },
            label = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.autofill(
                autofillTypes = listOf(AutofillType.EmailAddress),
                onFill = { textEmail = it }
            )
        )
        OutlinedTextField(
            value = textPassword,
            onValueChange = {
                textPassword = it
            },
            label = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.autofill(
                autofillTypes = listOf(AutofillType.Password),
                onFill = { textPassword = it }
            )
        )
        Button(
            onClick = {
                loginViewModel.loginUser(textEmail, textPassword)
            }
        ) {
            Text(text = "Login")
        }
    }

    authDataState.value.let {
        when(it) {
            UiState.Initial -> Log.d("login", "LoginScreen: Initial")
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Empty -> Log.d("login", "LoginScreen: Empty")
            is UiState.Error -> Log.d("login", "LoginScreen: error ${it.error}")
            is UiState.Success -> {
                Log.d("login", "LoginScreen: success ${it.data}")
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TalentEaseTheme {
        LoginScreen()
    }
}