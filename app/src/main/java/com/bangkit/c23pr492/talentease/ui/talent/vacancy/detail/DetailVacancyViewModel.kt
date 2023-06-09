package com.bangkit.c23pr492.talentease.ui.talent.vacancy.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.model.application.ApplyApplicationResponse
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class DetailVacancyViewModel(private val repository: TalentRepository) : ViewModel() {
    private val _positionState =
        MutableStateFlow<UiState<PositionItemModel>>(UiState.Initial)
    val positionState = _positionState.asStateFlow()

    private val _applyPositionState =
        MutableStateFlow<UiState<ApplyApplicationResponse>>(UiState.Initial)
    val applyPositionState = _positionState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getPositionByPositionId(token: String, positionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPositionByPositionId(token, positionId).collect { resource ->
                when (resource) {
                    Resource.Loading -> _positionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _positionState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _positionState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }
//    fun applyPosition(token: String, positionId: String, file: MultipartBody.Part) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.applyPosition(token, positionId, file)
//        }
//    }

    fun applyPosition(token: String, positionId: String, file: MultipartBody.Part) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.applyPosition(token, positionId, file).collect {
                when (it) {
                    Resource.Loading -> _applyPositionState.emit(UiState.Loading)
                    is Resource.Error -> _applyPositionState.emit(UiState.Error(it.error))
                    is Resource.Success -> {
                        _applyPositionState.emit(UiState.Success(it.data))
                    }
                }
            }
        }
    }

    fun prepareEvent(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _applyPositionState.collectLatest {
            when (it) {
                is UiState.Success -> _eventFlow.emit(UiEvents.NavigateEvent(token))
                else -> {
                    Log.d("upload", "prepareEvent: Error")
                }
            }
        }
    }
}