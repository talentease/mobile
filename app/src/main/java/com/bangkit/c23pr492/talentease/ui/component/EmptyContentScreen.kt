package com.bangkit.c23pr492.talentease.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bangkit.c23pr492.talentease.ui.values.textLarger

@Composable
fun EmptyContentScreen(@StringRes id: Int, modifier: Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = id),
            fontSize = textLarger,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}