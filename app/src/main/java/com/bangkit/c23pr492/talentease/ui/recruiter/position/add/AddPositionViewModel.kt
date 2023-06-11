package com.bangkit.c23pr492.talentease.ui.recruiter.position.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.c23pr492.talentease.data.RecruiterRepository
import com.bangkit.c23pr492.talentease.data.database.PositionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPositionViewModel(private val recruiterRepository: RecruiterRepository) : ViewModel() {
//    -position id
//    -position name
//    -position description
//    -position location
//    -position type
//    -company id
//    -position deadline
//    -position salary

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

    fun addPosition() = viewModelScope.launch(Dispatchers.IO) {
        recruiterRepository.addPosition(PositionEntity.position1)
    }
}