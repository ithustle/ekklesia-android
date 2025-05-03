package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaButton

@Composable
fun CreateStoryScreen(
    verse: String,
    addingStory: Boolean = false,
    bookNameWithVersicle: String,
    onPublishStory: (Pair<Color, Color>) -> Unit = {},
    onFinishPublishedStory: () -> Unit = {},
) {
    val (bgColor, textColor) = verseBackgroundColors.first()

    var selectedBgColor by remember { mutableStateOf<Pair<Color, Color>>(Pair(bgColor, textColor)) }
    var previousAddingStory by remember { mutableStateOf(addingStory) }

    LaunchedEffect(addingStory) {
        if (previousAddingStory && !addingStory) {
            onFinishPublishedStory()
        }
        previousAddingStory = addingStory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = selectedBgColor.first)
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(1f))

        Text(
            text = verse,
            color = selectedBgColor.second,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 20.dp)
        )

        Text(
            text = "- $bookNameWithVersicle",
            color = selectedBgColor.second,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.weight(1f))

        BackgroundColorStory(
            onSelectedBgColor = {
                selectedBgColor = it
            }
        )
        Spacer(Modifier.height(16.dp))

        EkklesiaButton(
            label = stringResource(R.string.publish_story),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            loading = addingStory,
            onClick = {
                onPublishStory(selectedBgColor)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateStoryScreenPrev() {
    CreateStoryScreen(
        verse = "Bem sei eu que tudo podes e que nada te é impossível.",
        bookNameWithVersicle = "Jó 42:2"
    )
}