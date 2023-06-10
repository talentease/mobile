package com.bangkit.c23pr492.talentease.ui.recruiter.position

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PositionViewModel(private val repository: RecruiterRepository) : ViewModel() {
    private val _listPositionState =
        MutableStateFlow<UiState<List<PositionItemModel>>>(UiState.Initial)
    val listPositionState = _listPositionState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    fun getAllPositions(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllPositions(token).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _listPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _listPositionState.emit(
                            if (resource.data.data.isNullOrEmpty()) UiState.Empty
                            else UiState.Success(resource.data.data)
                        )
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
                    is Resource.Loading -> _listPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        _listPositionState.emit(
                            if (resource.data.isNullOrEmpty()) UiState.Empty
                            else UiState.Success(resource.data)
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