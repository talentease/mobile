package com.bangkit.c23pr492.talentease.ui.recruiter.position.add

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.ui.core.UiEvents
import com.bangkit.c23pr492.talentease.utils.RecruiterViewModelFactory
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPositionScreen(
    token: String,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    addPositionViewModel: AddPositionViewModel = viewModel(
        factory = RecruiterViewModelFactory.getInstance(context)
    ),
    navigateToPosition: (String) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        addPositionViewModel.eventFlow.collectLatest {
            when(it) {
                is UiEvents.NavigateEvent -> navigateToPosition(it.route)
                else -> {}
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = addPositionViewModel.name,
            onValueChange = {
                addPositionViewModel.updateName(it)
            },
            label = {
                Text(text = "Position Name")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = addPositionViewModel.description,
            onValueChange = {
                addPositionViewModel.updateDescription(it)
            },
            label = {
                Text(text = "Position Description")
            },
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
                    value = addPositionViewModel.salary,
                    onValueChange = {
                        addPositionViewModel.updateSalary(it)
                    },
                    label = {
                        Text(text = "Position Salary")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                ExposedDropdownMenuBox(
                    expanded = addPositionViewModel.isExpanded,
                    onExpandedChange = { addPositionViewModel.isExpanded = it },
                ) {
                    OutlinedTextField(
                        value = addPositionViewModel.type,
                        onValueChange = {},
                        label = {
                            Text(text = "Type")
                        },
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = addPositionViewModel.isExpanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = addPositionViewModel.isExpanded,
                        onDismissRequest = { addPositionViewModel.isExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = "WFO")
                            },
                            onClick = {
                                addPositionViewModel.updateType("WFO")
                                addPositionViewModel.isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "WFA")
                            },
                            onClick = {
                                addPositionViewModel.updateType("WFA")
                                addPositionViewModel.isExpanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = "Hybrid")
                            },
                            onClick = {
                                addPositionViewModel.updateType("Hybrid")
                                addPositionViewModel.isExpanded = false
                            }
                        )
                    }
                }
            }
        }
        Row {
            var pickedDate by remember {
                mutableStateOf(LocalDate.now())
            }
            var pickedTime by remember {
                mutableStateOf(LocalTime.NOON)
            }
            addPositionViewModel.updateDeadline(
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

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    dateDialogState.show()
                }) {
                    Text(text = "Pick date")
                }
                Text(text = formattedDate)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    timeDialogState.show()
                }) {
                    Text(text = "Pick time")
                }
                Text(text = formattedTime)
            }
            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(text = "Ok") {
                        Toast.makeText(
                            context,
                            "Clicked ok",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    negativeButton(text = "Cancel")
                }
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
                buttons = {
                    positiveButton(text = "Ok") {
                        Toast.makeText(
                            context,
                            "Clicked ok",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    negativeButton(text = "Cancel")
                }
            ) {
                timepicker(
                    initialTime = pickedTime,
                    title = "Pick a time"
                ) {
                    pickedTime = it
                }
            }
        }
        Button(
            onClick = {
                addPositionViewModel.apply {
                    updatePosition(name, description, salary.toInt(), type, deadline)
                    addPosition(token, position)
                    prepareEvent(token)
                }
            }
        ) {
            Text(text = "Add Position")
        }
    }
}