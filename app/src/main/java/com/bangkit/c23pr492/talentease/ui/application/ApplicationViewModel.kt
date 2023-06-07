package com.bangkit.c23pr492.talentease.ui.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.MainRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.ApplicationModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApplicationViewModel(private val repository: MainRepository) : ViewModel() {
    private val _listApplicationState =
        MutableStateFlow<UiState<List<ApplicationModel>>>(UiState.Initial)
    val listApplicationState = _listApplicationState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun getAllApplications() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllApplications().collect { resource ->
                when (resource) {
                    is Resource.Loading -> _listApplicationState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _listApplicationState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _listApplicationState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun searchApplications(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchApplications(newQuery).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _listApplicationState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _listApplicationState.emit(
                            if (resource.data.isNotEmpty()) UiState.Success(resource.data) else UiState.Empty
                        )
                    }
                    is Resource.Error -> {
                        _listApplicationState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }
}