package com.toquemedia.ekklesia.ui.screens.bible.worship

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Slideshow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun VideoUploadedSuccessful(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    onDeleteVideo: () -> Unit = {}
) {
    Row(
        modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Rounded.Slideshow,
                contentDescription = "",
                tint = color,
            )
            Spacer(Modifier.width(4.dp))
            Text(text)
        }

        IconButton(onClick = onDeleteVideo) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = stringResource(R.string.delete_created_video),
                tint = color,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VideoUploadedSuccessfulPrev() {
    VideoUploadedSuccessful(
        text = "Video carregado com sucesso",
        color = PrincipalColor,
        onDeleteVideo = {}
    )
}