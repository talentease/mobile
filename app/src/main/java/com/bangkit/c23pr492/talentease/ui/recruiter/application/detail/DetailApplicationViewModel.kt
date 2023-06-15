package com.bangkit.c23pr492.talentease.ui.recruiter.application.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.application.ApplicationByIdModel
import com.bangkit.c23pr492.talentease.data.model.application.ApplicationUpdateResponse
import com.bangkit.c23pr492.talentease.data.model.position.StatusModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailApplicationViewModel(private val repository: RecruiterRepository) : ViewModel() {
    private val _applicationState =
        MutableStateFlow<UiState<ApplicationByIdModel>>(UiState.Initial)
    val applicationState = _applicationState.asStateFlow()

    private val _updateState =
        MutableStateFlow<UiState<ApplicationUpdateResponse>>(UiState.Initial)
    val updateState = _updateState.asStateFlow()


    fun getDetailApplicationById(token: String, applicationId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDetailApplicationById(token, applicationId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _applicationState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _applicationState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _applicationState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun updateStatus(token: String, applicationId: String, status: StatusModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateApplication(token, applicationId, status).collect {
                when (it) {
                    Resource.Loading -> _updateState.emit(UiState.Loading)
                    is Resource.Error -> _updateState.emit(UiState.Error(it.error))
                    is Resource.Success -> _updateState.emit(UiState.Success(it.data))
                }
            }
        }
    }
}