package com.bangkit.c23pr492.talentease.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.bangkit.c23pr492.talentease.ui.values.textLarge
import com.bangkit.c23pr492.talentease.ui.values.textRegular

@Composable
fun TitleText(modifier: Modifier = Modifier, string: String) {
    Text(
        text = string,
        fontWeight = FontWeight.SemiBold,
        fontSize = textLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun DescriptionText(modifier: Modifier = Modifier, string: String) {
    Text(
        text = string,
        fontWeight = FontWeight.Normal,
        fontSize = textRegular,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun RegularText(modifier: Modifier = Modifier, string: String) {
    Text(
        text = string,
        fontWeight = FontWeight.Normal,
        fontSize = textRegular,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun StatusAndPositionText(modifier: Modifier = Modifier, status: String, position: String) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = status,
            fontWeight = FontWeight.Medium,
            fontSize = textRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "/ $position",
            fontWeight = FontWeight.Normal,
            fontSize = textRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}