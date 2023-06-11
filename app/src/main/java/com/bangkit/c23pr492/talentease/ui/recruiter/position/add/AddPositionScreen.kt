package com.bangkit.c23pr492.talentease.ui.recruiter.position.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.c23pr492.talentease.utils.RecruiterViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPositionScreen(
    token: String,
    modifier: Modifier = Modifier,
    addPositionViewModel: AddPositionViewModel = viewModel(
        factory = RecruiterViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToPosition: (String) -> Unit,
) {
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
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Filled.Email,
//                    contentDescription = null
//                )
//            },
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
        Button(
            onClick = {
                addPositionViewModel.addPosition()
                navigateToPosition(token)
            }
        ) {
            Text(text = "Add Position")
        }
    }
//    Text(token)
}