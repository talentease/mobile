package com.bangkit.c23pr492.talentease.ui.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Repository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.ApplicationModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ApplicationViewModel(private val repository: Repository) : ViewModel() {
    private val _listApplicationState =
        MutableStateFlow<UiState<List<ApplicationModel>>>(UiState.Initial)
    val listApplicationState = _listApplicationState.asStateFlow()

    fun getLanguages() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getApplications().collect { resource ->
                when (resource) {
                    Resource.Loading -> _listApplicationState.emit(UiState.Loading)
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
}