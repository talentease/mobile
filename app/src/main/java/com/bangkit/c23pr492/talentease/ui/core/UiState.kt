package com.bangkit.c23pr492.talentease.ui.core

import com.bangkit.c23pr492.talentease.utils.UiText

sealed class UiState<out T: Any?> {
    object Initial : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val error: UiText) : UiState<Nothing>()
}