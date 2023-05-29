package com.bangkit.c23pr492.talentease.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Repository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _loginState = MutableStateFlow<UiState<String>>(UiState.Initial)
    val loginState = _loginState.asStateFlow()

    fun loginUser(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.loginUser(email, password).collect {
            when (it) {
                is Resource.Loading -> {
                    _loginState.emit(UiState.Loading)
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        _loginState.emit(
                            UiState.Success(
                                it.data.getIdToken(true).await().token.toString()
                            )
                        )
                        Log.d("login", "loginUser: email ${it.data.email}")
                    } else {
                        _loginState.emit(UiState.Empty)
                    }
                }
                is Resource.Error -> {
                    _loginState.emit(UiState.Error(it.error))
                }
            }
        }
    }
}