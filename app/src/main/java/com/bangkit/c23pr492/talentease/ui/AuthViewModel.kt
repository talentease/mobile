package com.bangkit.c23pr492.talentease.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.AuthRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.ui.navigation.Screen
import com.bangkit.c23pr492.talentease.utils.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _tokenState = MutableStateFlow<UiState<String>>(UiState.Initial)
    val tokenState = _tokenState.asStateFlow()

    private val _roleState = MutableStateFlow<UiState<String>>(UiState.Initial)
    val roleState = _roleState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getToken()
        getRole()
        prepareEvent()
    }

    fun prepareEvent() = viewModelScope.launch(Dispatchers.IO) {
        _tokenState.combine(_roleState) { token, role ->
            when (token) {
                is UiState.Success -> {
                    when (role) {
                        is UiState.Success -> {
                            when (role.data) {
                                "recruiter" -> return@combine UiEvents.NavigateEvent(
                                    Screen.Application.createRoute(
                                        token.data
                                    )
                                )
                                "candidate" -> return@combine UiEvents.NavigateEvent(
                                    Screen.Vacancy.createRoute(
                                        token.data
                                    )
                                )
                                else -> return@combine UiEvents.SnackBarEvent(UiText.DynamicString("Unknown Role"))
                            }
                        }
                        is UiState.Empty -> {
                            return@combine UiEvents.NavigateEvent(Screen.Login.route)
                        }
                        is UiState.Error -> {
                            return@combine UiEvents.SnackBarEvent(role.error)
                        }
                        is UiState.Initial -> {
                            return@combine UiEvents.NavigateEvent(Screen.Login.route)
                        }
                        is UiState.Loading -> {
                            return@combine UiEvents.Loading
                        }
                    }
                }
                is UiState.Empty -> {
                    return@combine UiEvents.NavigateEvent(Screen.Login.route)
                }
                is UiState.Error -> {
                    return@combine UiEvents.SnackBarEvent(token.error)
                }
                is UiState.Initial -> {
                    return@combine UiEvents.NavigateEvent(Screen.Login.route)
                }
                is UiState.Loading -> {
                    return@combine UiEvents.Loading
                }
            }
        }.collectLatest { value ->
            _eventFlow.emit(value)
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
                        repository.saveUserId(it.data.uid)
                        Log.d("login", "loginUser: masuk login $token")
                        checkRole(token, it.data.uid)
                        _tokenState.emit(UiState.Success(token))
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

    fun checkRole(token: String, uid: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.d("login", "checkRole: masuk")
        repository.getRoleById(token, uid).collect {
            when (it) {
                is Resource.Error -> _roleState.emit(UiState.Error(it.error))
                Resource.Loading -> _roleState.emit(UiState.Loading)
                is Resource.Success -> {
                    if (it.data.data.role.isEmpty()) {
                        Log.d("login", "loginUser: role empty ${it.data.data.role}")
                        _roleState.emit(UiState.Empty)
                    } else {
                        repository.saveRole(it.data.data.role)
                        Log.d("login", "loginUser: role ${it.data.data.role}")
                        _roleState.emit(UiState.Success(it.data.data.role))
                    }
                }
            }
        }
    }

    fun getToken() = viewModelScope.launch(Dispatchers.IO) {
        repository.getToken().collect {
            _tokenState.emit(UiState.Loading)
            Log.d("login", "getToken: $it")
            if (it.isNullOrBlank()) _tokenState.emit(UiState.Empty)
            else _tokenState.emit(UiState.Success(it))
        }
    }

    fun getRole() = viewModelScope.launch(Dispatchers.IO) {
        repository.getRole().collect {
            _roleState.emit(UiState.Loading)
            Log.d("login", "getRole: $it")
            if (it.isNullOrBlank()) _roleState.emit(UiState.Empty)
            else _roleState.emit(UiState.Success(it))
        }
    }
}