package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerseActionOption(
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false),
    modifier: Modifier = Modifier,
    onDismissRequest: (SheetState) -> Unit = {},
    onFavoriteVerse: () -> Unit = {},
    onShareVerse: () -> Unit = {},
    onAddNoteToVerse: () -> Unit = {},
    onSelectVerseForDevocional: () -> Unit = {}
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = {
            onDismissRequest(sheetState)
        }
    ) {
        ActionOptions(
            onFavoriteVerse = onFavoriteVerse,
            onShareVerse = onShareVerse,
            onAddNoteToVerse = onAddNoteToVerse,
            onSelectVerseForDevocional = onSelectVerseForDevocional
        )
    }
}

@Composable
fun ActionOption(
    modifier: Modifier = Modifier,
    actionOptionIcon: ImageVector,
    onActionOptionClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size = 8.dp))
            .background(color = PrincipalColor)
            .clickable {
                onActionOptionClick()
            }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = actionOptionIcon,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun ActionOptions(
    modifier: Modifier = Modifier,
    onFavoriteVerse: () -> Unit = {},
    onShareVerse: () -> Unit = {},
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
        ActionOption(
            modifier = modifier,
            actionOptionIcon = Icons.Rounded.Diversity3,
            onActionOptionClick = {
                onSelectVerseForDevocional()
            }
        )
        ActionOption(
            modifier = modifier,
            actionOptionIcon = Icons.Rounded.EditNote,
            onActionOptionClick = {
                onAddNoteToVerse()
            }
        )
        ActionOption(
            modifier = modifier,
            actionOptionIcon = Icons.Rounded.Share,
            onActionOptionClick = {
                onShareVerse()
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun VerseActionOptionPrev() {
    VerseActionOption(
        onDismissRequest = {},
        onFavoriteVerse = {},
        onShareVerse = {},
        onAddNoteToVerse = {},
    )
}