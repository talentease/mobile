package com.bangkit.c23pr492.talentease.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.c23pr492.talentease.data.model.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.theme.TalentEaseTheme

@Composable
fun PositionItems(position: PositionItemModel, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            SubTitleText(
                string = position.title ?: "Position Name",
                modifier = Modifier.padding(bottom = 12.dp)
            )
            DescriptionText(
                string = position.description ?: "Description",
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp)
            )
            RegularText(
                string = position.deadline ?: "Deadline Date",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PositionItemsPreview() {
    TalentEaseTheme {
        PositionItems(position = PositionItemModel())
    }
}