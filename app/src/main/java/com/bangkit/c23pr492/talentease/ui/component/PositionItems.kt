package com.bangkit.c23pr492.talentease.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.c23pr492.talentease.data.model.position.PositionItemModel
import com.bangkit.c23pr492.talentease.ui.theme.TalentEaseTheme

@Composable
fun PositionItems(token: String, position: PositionItemModel, modifier: Modifier = Modifier, navigateToDetail: (String, String) -> Unit) {
    Card(modifier.clickable {
        navigateToDetail(token, position.id)
    }) {
        Column(modifier = Modifier.padding(all = 16.dp)) {
            TitleText(
                string = position.title ?: "Position Name",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            DescriptionText(
                string = position.description ?: "Description",
                modifier = Modifier.padding(bottom = 12.dp)
            )
            RegularText(
                string = position.deadline ?: "Info",
                modifier = Modifier.padding()
            )
        }
    }
}

@Preview
@Composable
fun PositionItemsPreview() {
    TalentEaseTheme {
//        PositionItems(position = PositionItemModel())
    }
}