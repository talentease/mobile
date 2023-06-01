package com.bangkit.c23pr492.talentease.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex

@Composable
fun LoadingProgressBar(modifier: Modifier = Modifier.fillMaxSize(), isLoading: Boolean) {
    if (isLoading) {
        Box(modifier = modifier.zIndex(1f), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}