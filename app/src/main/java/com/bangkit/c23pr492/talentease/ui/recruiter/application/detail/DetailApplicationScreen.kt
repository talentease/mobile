package com.bangkit.c23pr492.talentease.ui.recruiter.application.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.c23pr492.talentease.ui.theme.TalentEaseTheme

@Composable
fun DetailYourApplicationScreen(
    token: String,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {
    //deskripsi posisi
    Card(modifier = Modifier.padding(20.dp)) {
        Column {
            Text(text = "Position Name")
            Text(text = "Location")
            Text(text = "Type")
            Text(text = "Salary")
            Text(text = "Desc")
        }
    }
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
    //data diri talent
    Card(modifier = Modifier.padding(20.dp)) {
        Column {
            Text(text = "Talent Name")
            Text(text = "Talent Age")
            Text(text = "Talent Location")
            Text(text = "Talent Contact")
            Text(text = "CV Link")
        }
    }
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
    //CV Talent
    Card(modifier = Modifier.padding(20.dp)) {
        Column {
            Text(text = "Talent Education")
            Text(text = "Talent Skills")
            Text(text = "Talent Experience")
            Text(text = "Talent Certification")
            Text(text = "Talent Award")
            Text(text = "Talent Publication")
        }
    }
    Spacer(modifier = Modifier.padding(vertical = 16.dp))
    //Status
    val statusList: List<String> = listOf(
        "Unread",
        "Screening",
        "Interview",
        "Accept",
        "Reject",
        "Active",
        "Expired",
    )
    var selected by rememberSaveable { mutableStateOf("") }
    Card(modifier = Modifier.padding(20.dp)) {
        statusList.forEach { title ->
            Chip(
                title = title,
                selected = selected,
                onSelected = {
                    selected = it
                }
            )
        }
    }
}

@Composable
fun Chip(
    title: String,
    selected: String,
    onSelected: (String) -> Unit
) {
    val isSelected = selected == title
    var surfaceColor: Color
    var contentColor: Color
    var boxModifier: Modifier
    MaterialTheme.colorScheme.apply {
        surfaceColor = if (isSelected) secondaryContainer else surface
        contentColor = if (isSelected) onSecondaryContainer else onSurfaceVariant
        boxModifier = if (isSelected) {
            Modifier.background(color = onSecondaryContainer.copy(alpha = 0.12f))
        } else {
            Modifier
                .background(color = onSurfaceVariant.copy(alpha = 0.12f))
                .border(Dp(1f), color = outline, shape = MaterialTheme.shapes.large)
        }
    }

    Surface(
        modifier = Modifier
            .background(surfaceColor)
            .clip(MaterialTheme.shapes.large)
    ) {
        Box(
            modifier = boxModifier
                .clip(MaterialTheme.shapes.large)
                .clickable {
                    onSelected(title)
                }
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(visible = isSelected) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "check",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(
                    text = title,
                    color = contentColor,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}



@Preview
@Composable
fun DetailApplicationScreenPreview() {
    TalentEaseTheme {
        DetailYourApplicationScreen(token = "")
    }
}