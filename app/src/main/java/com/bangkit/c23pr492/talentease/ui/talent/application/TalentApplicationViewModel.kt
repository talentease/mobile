package com.bangkit.c23pr492.talentease.ui.talent.application

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.TalentRepository
import com.bangkit.c23pr492.talentease.data.model.application.DataItem
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TalentApplicationViewModel(private val repository: TalentRepository) : ViewModel() {
    private val _listALlApplicationState =
        MutableStateFlow<UiState<List<DataItem>>>(UiState.Initial)
    val listALlApplicationState = _listALlApplicationState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    var dataItem by mutableStateOf(listOf<DataItem>())
        private set

    fun getAllTalentApplication(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTalentId().collect { uid ->
                repository.getAllTalentApplicationWithTalentId(token, uid ?: "")
                    .collect { resource ->
                        when (resource) {
                            Resource.Loading -> _listALlApplicationState.emit(UiState.Loading)
                            is Resource.Success -> {
                                resource.data.data.let {
                                    _listALlApplicationState.emit(
                                        UiState.Success(it)
                                    )
                                }
                            }
                            is Resource.Error -> {
                                if (resource.error.toString()
                                        .contains("Profile not found", ignoreCase = true)
                                ) {
                                    _listALlApplicationState.emit(UiState.Empty)
                                } else {
                                    _listALlApplicationState.emit(UiState.Error(resource.error))
                                }
                            }
                        }
                    }
            }
        }
    }

    fun searchPositions(token: String, newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch(Dispatchers.IO) {
            if (newQuery.isNotBlank() && newQuery.isNotEmpty()) {
                val filterItem = dataItem.filter {
                    val companyAndPosition = it.position.company.name + it.position.title
                    companyAndPosition.contains(newQuery, ignoreCase = true)
                }
                _listALlApplicationState.emit(UiState.Success(filterItem.toMutableList()))
            } else getAllTalentApplication(token)
        }
    }
}