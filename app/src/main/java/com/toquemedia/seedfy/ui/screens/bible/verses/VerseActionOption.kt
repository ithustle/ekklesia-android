package com.toquemedia.seedfy.ui.screens.bible.verses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.ui.composables.EkklesiaModalSheet
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerseActionOption(
    hasNote: Boolean,
    hasDevocional: Boolean,
    onDismissRequest: (SheetState) -> Unit = {},
    onFavoriteVerse: () -> Unit = {},
    onAddStory: () -> Unit = {},
    onAddNoteToVerse: () -> Unit = {},
    onSelectVerseForDevocional: () -> Unit = {}
) {
    EkklesiaModalSheet(
        onDismissRequest = onDismissRequest
    ) {
        ActionOptions(
            onFavoriteVerse = onFavoriteVerse,
            onAddStory = onAddStory,
            onAddNoteToVerse = onAddNoteToVerse,
            onSelectVerseForDevocional = onSelectVerseForDevocional,
            hasNote = hasNote,
            hasDevocional = hasDevocional,
        )
    }
}

@Composable
fun ActionOption(
    modifier: Modifier = Modifier,
    hasBackgroundColor: Boolean = true,
    actionOptionIcon: ImageVector? = null,
    actionOptionPainterIcon: Painter? = null,
    onActionOptionClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 8.dp))
            .background(color = if (hasBackgroundColor) PrincipalColor else Color.Transparent)
            .clickable {
                onActionOptionClick()
            }
            .padding(16.dp)
    ) {

        if (actionOptionIcon != null) {
            Icon(
                imageVector = actionOptionIcon,
                contentDescription = null,
                tint = Color.White
            )
        }

        if (actionOptionPainterIcon != null) {
            Icon(
                painter = actionOptionPainterIcon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun ActionOptions(
    modifier: Modifier = Modifier,
    hasNote: Boolean,
    hasDevocional: Boolean,
    onFavoriteVerse: () -> Unit = {},
    onAddStory: () -> Unit = {},
    onAddNoteToVerse: () -> Unit = {},
    onSelectVerseForDevocional: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionOption(
            modifier = modifier,
            actionOptionIcon = Icons.Rounded.Favorite,
            onActionOptionClick = {
                onFavoriteVerse()
            }
        )
        if (!hasDevocional) {
            ActionOption(
                modifier = modifier,
                actionOptionIcon = Icons.Rounded.Diversity3,
                onActionOptionClick = {
                    onSelectVerseForDevocional()
                }
            )
        }
        if (!hasNote) {
            ActionOption(
                modifier = modifier,
                actionOptionIcon = Icons.Rounded.EditNote,
                onActionOptionClick = {
                    onAddNoteToVerse()
                }
            )
        }
        ActionOption(
            modifier = modifier,
            actionOptionPainterIcon = painterResource(R.drawable.story),
            onActionOptionClick = {
                onAddStory()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun VerseActionOptionPrev() {
    VerseActionOption(
        hasDevocional = false,
        hasNote = false,
        onDismissRequest = {},
        onFavoriteVerse = {},
        onAddStory = {},
        onAddNoteToVerse = {},
    )
}