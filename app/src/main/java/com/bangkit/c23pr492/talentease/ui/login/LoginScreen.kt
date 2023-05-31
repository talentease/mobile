package com.bangkit.c23pr492.talentease.ui.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.R
import com.bangkit.c23pr492.talentease.ui.common.UiState
import com.bangkit.c23pr492.talentease.ui.theme.TalentEaseTheme
import com.bangkit.c23pr492.talentease.utils.ViewModelFactory
import com.bangkit.c23pr492.talentease.utils.autofill

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    loginViewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToHome: (String) -> Unit,
    navigateToRegister: () -> Unit,
) {
    val tokenState = loginViewModel.tokenState.collectAsState()
    tokenState.value.let {
        when (it) {
            UiState.Initial -> loginViewModel.getToken()
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Empty -> LoginScreenContent(
                modifier,
                loginViewModel,
                navigateToHome,
                navigateToRegister
            )
            is UiState.Error -> Log.d("login", "LoginScreen: error ${it.error}")
            is UiState.Success -> {
                Log.d("login", "LoginScreen: success ${it.data}")
                navigateToHome(it.data)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreenContent(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    navigateToHome: (String) -> Unit,
    navigateToRegister: () -> Unit
) {
    val authDataState = loginViewModel.loginState.collectAsState()
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 32.dp, vertical = 64.dp)
    ) {
        var textEmail by rememberSaveable { mutableStateOf("") }
        var textPassword by rememberSaveable { mutableStateOf("") }
        Image(
            painter = painterResource(id = R.drawable.ic_talentease),
            contentDescription = null,
            modifier = Modifier.padding(top = 40.dp)
        )
        Column {
            Text(
                text = "Login",
                fontWeight = FontWeight.Medium,
                fontSize = 36.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Easily track your applications and talents",
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Text(
                text = "Email",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            TextField(
                value = textEmail,
                onValueChange = {
                    textEmail = it
                },
                placeholder = {
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
                modifier = Modifier
                    .autofill(
                        autofillTypes = listOf(AutofillType.EmailAddress),
                        onFill = { textEmail = it }
                    )
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Password",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            TextField(
                value = textPassword,
                onValueChange = {
                    textPassword = it
                },
                placeholder = {
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
                modifier = Modifier
                    .autofill(
                        autofillTypes = listOf(AutofillType.Password),
                        onFill = { textPassword = it }
                    )
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    loginViewModel.loginUser(textEmail, textPassword)
                },
                modifier = Modifier.fillMaxWidth().padding(top = 48.dp)
            ) {
                Text(text = "Log In")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.padding(top = 24.dp).fillMaxWidth()
            ) {
                Text(
                    text = "Need an account?",
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = "Register here",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { navigateToRegister() },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    authDataState.value.let {
        when (it) {
            UiState.Initial -> Log.d("login", "LoginScreen: Initial")
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Empty -> Log.d("login", "LoginScreen: Empty")
            is UiState.Error -> Log.d("login", "LoginScreen: error ${it.error}")
            is UiState.Success -> {
                Log.d("login", "LoginScreen: success ${it.data}")
                navigateToHome(it.data)
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TalentEaseTheme {
        LoginScreen(navigateToRegister = {}, navigateToHome = {})
    }
}