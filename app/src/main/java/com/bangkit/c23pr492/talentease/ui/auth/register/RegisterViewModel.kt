package com.bangkit.c23pr492.talentease.ui.auth.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.ui.component.AuthTextFieldViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModel : AuthTextFieldViewModel() {
    override var email by mutableStateOf("")
        private set

    override val emailNotValidState: StateFlow<Boolean> =
        snapshotFlow { email }
            .mapLatest {
                Patterns.EMAIL_ADDRESS.matcher(it).matches()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2_000),
                initialValue = false
            )

    override fun updateEmail(input: String) {
        email = input
    }

    override var password: String by mutableStateOf("")
        private set

    override val passwordNotValidState: StateFlow<Boolean> =
        snapshotFlow { password }
            .mapLatest {
                it.length < 8 && it.isNotBlank()
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(2_000),
                initialValue = false
            )

    override fun updatePassword(input: String) {
        password = input
    }
}