package com.toquemedia.ekklesia.ui.screens.bible.verses

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Diversity3
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.ui.composables.EkklesiaModalSheet
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun VerseActionOption(
    sheetState: ModalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    ),
    hasNote: Boolean,
    hasDevocional: Boolean,
    //onDismissRequest: (SheetState) -> Unit = {},
    onFavoriteVerse: () -> Unit = {},
    onShareVerse: () -> Unit = {},
    onAddNoteToVerse: () -> Unit = {},
    onSelectVerseForDevocional: () -> Unit = {}
) {
    EkklesiaModalSheet(
        sheetState = sheetState,
        sheetContent = {

        }
    ) {
        ActionOptions(
            onFavoriteVerse = onFavoriteVerse,
            onShareVerse = onShareVerse,
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
    hasNote: Boolean,
    hasDevocional: Boolean,
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
            actionOptionIcon = Icons.Rounded.Share,
            onActionOptionClick = {
                onShareVerse()
            }
        )
    }
}


@Preview
@Composable
private fun VerseActionOptionPrev() {
    VerseActionOption(
        hasDevocional = false,
        hasNote = false,
        //onDismissRequest = {},
        onFavoriteVerse = {},
        onShareVerse = {},
        onAddNoteToVerse = {},
    )
}