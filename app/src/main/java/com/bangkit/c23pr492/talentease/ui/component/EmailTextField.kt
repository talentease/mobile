package com.bangkit.c23pr492.talentease.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.bangkit.c23pr492.talentease.utils.autofill

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EmailTextField(viewModel: TextFieldViewModel) {
    TextField(
        value = viewModel.email,
        onValueChange = {
            viewModel.updateEmail(it)
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
                onFill = { viewModel.updateEmail(it) }
            )
            .fillMaxWidth()
    )
}