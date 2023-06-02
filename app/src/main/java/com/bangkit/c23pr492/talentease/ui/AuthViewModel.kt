package com.bangkit.c23pr492.talentease.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Repository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.ui.common.UiEvents
import com.bangkit.c23pr492.talentease.ui.common.UiState
import com.bangkit.c23pr492.talentease.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(private val repository: Repository) : ViewModel() {

    private val _tokenState = MutableStateFlow<UiState<String>>(UiState.Initial)
    val tokenState = _tokenState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getToken()
    }

    fun isLogin() = viewModelScope.launch(Dispatchers.IO) {
        _tokenState.collectLatest {
            when (it) {
                is UiState.Success -> {
                    _eventFlow.emit(UiEvents.NavigateEvent(Screen.Home.createRoute(it.data)))
                }
                is UiState.Empty -> {
                    _eventFlow.emit(UiEvents.NavigateEvent(Screen.Login.route))
                }
                is UiState.Error -> {
                    _eventFlow.emit(UiEvents.SnackBarEvent(it.error))
                }
                is UiState.Initial -> {
                    _eventFlow.emit(UiEvents.NavigateEvent(Screen.Login.route))
                }
                is UiState.Loading -> {
                    _eventFlow.emit(UiEvents.Loading)
                }
            }
        }
    }

    fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        repository.getToken().collect {
            _tokenState.emit(UiState.Loading)
            if (it.isNullOrBlank()) _tokenState.emit(UiState.Empty)
            else _tokenState.emit(UiState.Success(it))
        }
    }

    fun login(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.loginUser(email, password).collect {
            when (it) {
                is Resource.Loading -> {
                    _tokenState.emit(UiState.Loading)
                }
                is Resource.Success -> {
                    if (it.data != null) {
                        val token = it.data.getIdToken(true).await().token.toString()
                        repository.saveToken(token)
                        _tokenState.emit(UiState.Success(token))
                        Log.d("login", "loginUser: email ${it.data.email}")
                    } else {
                        _tokenState.emit(UiState.Empty)
                    }
                }
                is Resource.Error -> {
                    _tokenState.emit(UiState.Error(it.error))
                }
            }
        }
    }

    fun register(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.registerUser(email, password).collect {
            when (it) {
                is Resource.Loading -> _eventFlow.emit(UiEvents.Loading)
                is Resource.Success -> _eventFlow.emit(UiEvents.NavigateEvent(Screen.Login.route))
                is Resource.Error -> _eventFlow.emit(UiEvents.SnackBarEvent(it.error))
            }
        }
    }

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        repository.logoutUser().collect {
            when (it) {
                is Resource.Loading -> _tokenState.emit(UiState.Loading)
                is Resource.Success -> _tokenState.emit(UiState.Empty)
                is Resource.Error -> _tokenState.emit(UiState.Error(it.error))
            }
        }
    }

}