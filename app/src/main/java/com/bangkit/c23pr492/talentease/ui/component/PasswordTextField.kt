package com.bangkit.c23pr492.talentease.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.bangkit.c23pr492.talentease.utils.autofill

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordTextField(viewModel: AuthTextFieldViewModel) {
    Box {
        TextField(
            value = viewModel.password,
            onValueChange = {
                viewModel.updatePassword(it)
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
                    onFill = { viewModel.updatePassword(it) }
                )
                .fillMaxWidth()
        )
    }
}