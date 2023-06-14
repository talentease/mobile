package com.bangkit.c23pr492.talentease.ui.recruiter.position.detail

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.component.LoadingProgressBar
import com.bangkit.c23pr492.talentease.ui.core.UiState
import com.bangkit.c23pr492.talentease.utils.RecruiterViewModelFactory
import com.bangkit.c23pr492.talentease.utils.UiText.Companion.asString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun DetailPositionScreen(
    token: String,
    positionId: String,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    detailPositionViewModel: DetailPositionViewModel = viewModel(
        factory = RecruiterViewModelFactory.getInstance(context)
    ),
) {
    Log.d("position", "DetailPositionScreen: $positionId")
    val detailPositionState = detailPositionViewModel.detailPositionState.collectAsState()
    var isLoading by rememberSaveable { mutableStateOf(false) }
    LoadingProgressBar(isLoading = isLoading)
    detailPositionState.value.let { state ->
        when (state) {
            UiState.Empty -> {}
            UiState.Initial -> {
                isLoading = false
                detailPositionViewModel.getDetailPosition(token, positionId)
            }
            UiState.Loading -> isLoading = true
            is UiState.Error -> {
                isLoading = false
                Toast.makeText(
                    LocalContext.current,
                    state.error.asString(LocalContext.current),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is UiState.Success -> {
                isLoading = false
                DetailPositionContentScreen(
                    token,
                    positionId,
                    position = state.data,
                    detailPositionViewModel = detailPositionViewModel
                )
            }
        }
    }
}

@Composable
fun DetailPositionContentScreen(
    token: String,
    positionId: String,
    position: PositionItemModel,
    detailPositionViewModel: DetailPositionViewModel
) {
    var readOnly by rememberSaveable { mutableStateOf(true) }
    detailPositionViewModel.apply {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Card {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(text = position.company?.name ?: "")
                    Text(text = position.company?.address ?: "")
                }
            }
            Card {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            updateName(it)
                        },
                        label = {
                            Text(text = "Position Name")
                        },
                        placeholder = {
                            Text(text = name)
                        },
                        readOnly = readOnly,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            updateDescription(it)
                        },
                        placeholder = {
                            Text(text = description)
                        },
                        readOnly = readOnly,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = salary,
                                onValueChange = {
                                    updateSalary(it)
                                },
                                placeholder = {
                                    Text(text = salary)
                                },
                                readOnly = readOnly,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                ),
                                singleLine = true
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            if (readOnly) {
                                OutlinedTextField(
                                    value = type,
                                    onValueChange = {},
                                    placeholder = {
                                        Text(text = type)
                                    },
                                    readOnly = true,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    ),
                                    singleLine = true
                                )
                            } else {
                                TypeSelector(detailPositionViewModel = this@apply)
                            }
                        }
                    }
                    if (readOnly) {
                        OutlinedTextField(
                            value = deadline,
                            onValueChange = { },
                            placeholder = {
                                Text(text = deadline)
                            },
                            readOnly = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        DateTimepicker(detailPositionViewModel = this@apply)
                    }
                }
            }
            Button(
                onClick = {
                    readOnly = if (readOnly) {
                        false
                    } else {
                        detailPositionViewModel.apply {
                            updatePositionModel(name, description, salary.toInt(), type, deadline)
                            updatePosition(token, positionId, this.position)
                        }
                        true
                    }
                }
            ) {
                if (readOnly) {
                    Text(text = "Edit Position")
                } else {
                    Text(text = "Save Changes")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeSelector(detailPositionViewModel: DetailPositionViewModel) {
    ExposedDropdownMenuBox(
        expanded = detailPositionViewModel.isExpanded,
        onExpandedChange = { detailPositionViewModel.isExpanded = it },
    ) {
        OutlinedTextField(
            value = detailPositionViewModel.type,
            onValueChange = {},
            label = {
                Text(text = "Type")
            },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = detailPositionViewModel.isExpanded)
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = detailPositionViewModel.isExpanded,
            onDismissRequest = { detailPositionViewModel.isExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "WFO")
                },
                onClick = {
                    detailPositionViewModel.updateType("WFO")
                    detailPositionViewModel.isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "WFA")
                },
                onClick = {
                    detailPositionViewModel.updateType("WFA")
                    detailPositionViewModel.isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Hybrid")
                },
                onClick = {
                    detailPositionViewModel.updateType("Hybrid")
                    detailPositionViewModel.isExpanded = false
                }
            )
        }
    }
}

@Composable
fun DateTimepicker(detailPositionViewModel: DetailPositionViewModel) {
    Row {
        var pickedDate by remember {
            mutableStateOf(LocalDate.now())
        }
        var pickedTime by remember {
            mutableStateOf(LocalTime.NOON)
        }
        detailPositionViewModel.updateDeadline(
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'").format(pickedDate) +
                    DateTimeFormatter.ofPattern("HH:mm:ss.SSS'Z'").format(pickedTime)
        )
        val formattedDate by remember {
            derivedStateOf {
                DateTimeFormatter
                    .ofPattern("MMM dd yyyy")
                    .format(pickedDate)
            }
        }
        val formattedTime by remember {
            derivedStateOf {
                DateTimeFormatter
                    .ofPattern("hh:mm")
                    .format(pickedTime)
            }
        }

        val dateDialogState = rememberMaterialDialogState()
        val timeDialogState = rememberMaterialDialogState()
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { dateDialogState.show() }
            ) {
                Text(text = "Pick date")
            }
            Text(text = formattedDate)
        }
        Spacer(modifier = Modifier.padding(16.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { timeDialogState.show() }
            ) {
                Text(text = "Pick time")
            }
            Text(text = formattedTime)
        }
        MaterialDialog(
            dialogState = dateDialogState,
        ) {
            datepicker(
                initialDate = pickedDate,
                title = "Pick a date",
                allowedDateValidator = {
                    it >= LocalDate.now()
                }
            ) {
                pickedDate = it
            }
        }
        MaterialDialog(
            dialogState = timeDialogState,
        ) {
            timepicker(
                initialTime = pickedTime,
                title = "Pick a time"
            ) {
                pickedTime = it
            }
        }
    }
}
