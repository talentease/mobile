package com.bangkit.c23pr492.talentease.ui.component

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class AuthTextFieldViewModel : ViewModel() {
    abstract val email: String

    abstract val emailNotValidState: StateFlow<Boolean>

    abstract fun updateEmail(input: String)

    abstract val password: String

    abstract val passwordNotValidState: StateFlow<Boolean>

    abstract fun updatePassword(input: String)
}