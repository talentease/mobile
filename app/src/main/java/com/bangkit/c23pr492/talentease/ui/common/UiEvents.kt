package com.bangkit.c23pr492.talentease.ui.common

import com.bangkit.c23pr492.talentease.utils.UiText

sealed class UiEvents {
    object Loading : UiEvents()
    data class NavigateEvent(val route: String): UiEvents()
    data class SnackBarEvent(val error : UiText) : UiEvents()
}
