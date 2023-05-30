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

    private val _tokenState = MutableStateFlow<UiState<String>>(UiState.Initial)
    val tokenState = _tokenState.asStateFlow()

    fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        repository.getToken().collect {
            _tokenState.emit(UiState.Loading)
            if (it.isNullOrBlank()) _tokenState.emit(UiState.Empty)
            else _tokenState.emit(UiState.Success(it))
        }
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.loginUser(email, password).collect {
            when (it) {
                is Resource.Loading -> {
                    _loginState.emit(UiState.Loading)
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        val token = it.data.getIdToken(true).await().token.toString()
                        repository.saveToken(token)
                        _loginState.emit(
                            UiState.Success(
                                token
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