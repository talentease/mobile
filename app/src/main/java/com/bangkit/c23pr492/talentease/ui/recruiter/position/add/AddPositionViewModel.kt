package com.bangkit.c23pr492.talentease.ui.recruiter.position.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.Resource
import com.bangkit.c23pr492.talentease.data.model.position.PositionModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionResponse
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.ui.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddPositionViewModel(private val recruiterRepository: RecruiterRepository) : ViewModel() {
    private val _addPositionState = MutableStateFlow<UiState<PositionResponse>>(UiState.Initial)
    val addPositionState = _addPositionState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

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

    fun updatePosition(
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

    fun addPosition(token: String, position: PositionModel) =
        viewModelScope.launch(Dispatchers.IO) {
            recruiterRepository.uploadPosition(token, position).collect {
                when (it) {
                    Resource.Loading -> _addPositionState.emit(UiState.Loading)
                    is Resource.Error -> _addPositionState.emit(UiState.Error(it.error))
                    is Resource.Success -> _addPositionState.emit(UiState.Success(it.data))
                }
            }
        }

    fun prepareEvent(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _addPositionState.collectLatest {
            when (it) {
                UiState.Loading -> _eventFlow.emit(UiEvents.Loading)
                is UiState.Error -> _eventFlow.emit(UiEvents.SnackBarEvent(it.error))
                is UiState.Success -> _eventFlow.emit(
                    UiEvents.NavigateEvent(
                        Screen.Position.createRoute(
                            token
                        )
                    )
                )
                else -> {}
            }
        }
    }
}