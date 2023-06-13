package com.bangkit.c23pr492.talentease.ui.recruiter.application.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.c23pr492.talentease.ui.theme.TalentEaseTheme


@Composable
fun DetailRecruiterApplicationScreen(
    token: String,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {
    val emailAddress = "a200dkx4783@bangkit.academy"
    val listState = rememberLazyListState()
//    var emailSubject by rememberSaveable { mutableStateOf("") }
//    var emailBody by rememberSaveable { mutableStateOf("") }
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:$emailAddress")
                    }
                    context.startActivity(intent)
                }
            ) {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Send an Email")
            }
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(innerPadding).padding(20.dp)
        ) {
            //deskripsi posisi
            Card {
                Column {
                    Text(text = "Position Name")
                    Text(text = "Location")
                    Text(text = "Type")
                    Text(text = "Salary")
                    Text(text = "Desc")
                }
            }
            //data diri talent
            Card {
                Column {
                    Text(text = "Talent Name")
                    Text(text = "Talent Age")
                    Text(text = "Talent Location")
                    Text(text = "Talent Contact")
                    Text(text = "CV Link")
                }
            }
            //CV Talent
            Card {
                Column {
                    Text(text = "Talent Education")
                    Text(text = "Talent Skills")
                    Text(text = "Talent Experience")
                    Text(text = "Talent Certification")
                    Text(text = "Talent Award")
                    Text(text = "Talent Publication")
                }
            }
            //Status
            val statusList: List<String> = listOf(
                "Pending",
                "Screening",
                "Interview",
                "Accept",
                "Reject",
                "Active",
                "Expired",
            )
            var selected by rememberSaveable { mutableStateOf("") }
            LazyRow(state = listState, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(statusList, key = { it }) { status ->
                    Chip(
                        title = status,
                        selected = selected,
                        onSelected = {
                            selected = it
                        }
                    )
                }
                item { }
            }
//        OutlinedTextField(
//            value = emailSubject,
//            onValueChange = {
//                emailSubject = it
//            },
//            label = {
//                Text(text = "Email Subject")
//            }
//        )
//        OutlinedTextField(
//            value = emailBody,
//            onValueChange = {
//                emailBody = it
//            },
//            label = {
//                Text(text = "Email Body")
//            }
//        )
//            Button(
//                onClick = {
////                val uri = Uri.parse("mailto:$emailAddress")
////                    .buildUpon()
////                    .appendQueryParameter("subject", emailSubject)
////                    .appendQueryParameter("body", emailBody)
////                    .build()
////
////                val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
////                context.startActivity(Intent.createChooser(emailIntent, "Email via..."))
//                    val intent = Intent(Intent.ACTION_SENDTO).apply {
//                        data = Uri.parse("mailto:$emailAddress")
//                    }
//                    context.startActivity(intent)
//                }
//            ) {
//                Text(text = "Update")
//            }
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
//    var surfaceColor: Color
    var contentColor: Color
    var boxModifier: Modifier
    MaterialTheme.colorScheme.apply {
//        surfaceColor =
//            if (isSelected) onSecondaryContainer.copy(alpha = 0.12f) else onSurfaceVariant.copy(
//                alpha = 0.12f
//            )
        contentColor = if (isSelected) onSecondaryContainer else onSurfaceVariant
        boxModifier = if (isSelected) {
            Modifier
                .clip(MaterialTheme.shapes.large)
                .background(color = secondaryContainer)
        } else {
            Modifier
                .clip(MaterialTheme.shapes.large)
                .background(color = surface)
                .border(Dp(1f), color = outline, shape = MaterialTheme.shapes.large)
        }
    }

    Box(
        modifier = boxModifier
            .clickable {
                onSelected(title)
            }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
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
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 2.dp)
            )
        }
    }
}


@Preview
@Composable
fun DetailApplicationScreenPreview() {
    TalentEaseTheme {
        DetailRecruiterApplicationScreen(token = "")
    }
}