package com.bangkit.c23pr492.talentease.ui.recruiter.position.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionResponse
import com.bangkit.c23pr492.talentease.ui.core.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailPositionViewModel(private val repository: RecruiterRepository): ViewModel() {
    private val _detailPositionState =
        MutableStateFlow<UiState<PositionItemModel>>(UiState.Initial)
    val detailPositionState = _detailPositionState.asStateFlow()

    private val _updatePositionState = MutableStateFlow<UiState<PositionResponse>>(UiState.Initial)
    val updatePositionState = _updatePositionState.asStateFlow()

    fun getDetailPosition(token: String, positionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPositionByPositionID(token, positionId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> _detailPositionState.emit(UiState.Loading)
                    is Resource.Success -> {
                        name = resource.data.title.toString()
                        description = resource.data.description.toString()
                        salary = resource.data.salary.toString()
                        type = resource.data.type.toString()
                        deadline = resource.data.deadline.toString()
                        _detailPositionState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _detailPositionState.emit(UiState.Error(resource.error))
                    }
                }
            }
        }
    }

    fun updatePosition(token: String, positionId: String, position: PositionModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePosition(token, positionId, position).collect {
                when (it) {
                    Resource.Loading -> _updatePositionState.emit(UiState.Loading)
                    is Resource.Error -> _updatePositionState.emit(UiState.Error(it.error))
                    is Resource.Success -> _updatePositionState.emit(UiState.Success(it.data))
                }
            }
        }
    }

    var name by mutableStateOf("")
        private set

    fun updateName(input: String) {
        name = input
    }

    var description by mutableStateOf("")
        private set

    fun updateDescription(input: String) {
        description = input
    }

    var salary by mutableStateOf("")
        private set

    fun updateSalary(input: String) {
        salary = input
    }

    var isExpanded by mutableStateOf(false)

    var type by mutableStateOf("")
        private set

    fun updateType(input: String) {
        type = input
    }

    var deadline by mutableStateOf("")
        private set

    fun updateDeadline(input: String) {
        deadline = input
    }

    var position by mutableStateOf(
        PositionModel(
            title = name,
            description = description,
            salary = if (salary.isEmpty()) 0 else salary.toInt(),
            type = type,
            deadline = deadline
        )
    )
        private set

    fun updatePositionModel(
        name: String,
        description: String,
        salary: Int,
        type: String,
        deadline: String
    ) {
        position = PositionModel(
            title = name,
            description = description,
            salary = salary,
            type = type,
            deadline = deadline
        )
    }
}