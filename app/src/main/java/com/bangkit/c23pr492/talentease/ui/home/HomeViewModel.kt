package com.bangkit.c23pr492.talentease.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Repository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val _tokenState = MutableStateFlow<UiState<Unit>>(UiState.Initial)
    val tokenState = _tokenState.asStateFlow()

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        repository.logoutUser().collect {
            when(it) {
                is Resource.Loading -> _tokenState.emit(UiState.Loading)
                is Resource.Success -> _tokenState.emit(UiState.Success(it.data))
                is Resource.Error -> _tokenState.emit(UiState.Error(it.error))
            }
        }
    }
}