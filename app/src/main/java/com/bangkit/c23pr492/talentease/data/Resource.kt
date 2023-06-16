package com.bangkit.c23pr492.talentease.data

import com.bangkit.c23pr492.talentease.utils.UiText

sealed class Resource<out R> private constructor(){
    data class Success<out T>(val data: T): Resource<T>()
    data class Error(val error: UiText): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}
