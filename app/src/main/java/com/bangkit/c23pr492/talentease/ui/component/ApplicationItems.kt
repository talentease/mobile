package com.bangkit.c23pr492.talentease.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bangkit.c23pr492.talentease.data.model.ApplicationModel
import com.bangkit.c23pr492.talentease.ui.values.spacingRegular
import com.bangkit.c23pr492.talentease.ui.values.textLarge
import com.bangkit.c23pr492.talentease.ui.values.textRegular

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ApplicationItems(
    application: ApplicationModel,
    modifier: Modifier = Modifier,
//    navigateToDetail: (Int) -> Unit,
) {
    application.apply {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .combinedClickable(
                    onClick = {

                    },
                    onLongClick = {

                    }
                )
                .clickable {
//                    navigateToDetail(id)
                }
                .padding(spacingRegular)
        ) {
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(end = spacingRegular)
                    .size(54.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = textLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = position,
                    fontWeight = FontWeight.Normal,
                    fontSize = textRegular,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}