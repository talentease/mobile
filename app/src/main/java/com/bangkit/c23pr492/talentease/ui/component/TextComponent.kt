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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit

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

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    linkText: List<String>,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Medium,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    hyperlinks: List<String> = listOf("https://stevdza-san.com"),
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}