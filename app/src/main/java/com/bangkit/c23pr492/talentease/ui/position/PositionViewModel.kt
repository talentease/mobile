package com.bangkit.c23pr492.talentease.ui.position

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.MainRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.PositionModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PositionViewModel(private val repository: MainRepository) : ViewModel() {
    private val _listPositionState =
        MutableStateFlow<UiState<List<PositionModel>>>(UiState.Initial)
    val listPositionState = _listPositionState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun getAllPositions(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPositions(token).collect { resource ->
                when (resource) {
                    Resource.Loading -> _listPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _listPositionState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _listPositionState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun searchPositions(token: String, newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchPositions(token, newQuery).collect { resource ->
                when (resource) {
                    Resource.Loading -> _listPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _listPositionState.emit(
                            if (resource.data.isNotEmpty()) UiState.Success(resource.data) else UiState.Empty
                        )
                    }
                    is Resource.Error -> {
                        _listPositionState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }
}