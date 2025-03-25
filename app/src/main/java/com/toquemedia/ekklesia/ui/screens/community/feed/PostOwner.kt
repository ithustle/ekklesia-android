package com.toquemedia.ekklesia.ui.screens.community.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage

@Composable
fun PostOwner() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        EkklesiaImage(
            model = "photo".toUri(),
            contentDescription = stringResource(R.string.profileTitleScreen),
            modifier = Modifier
                .padding(end = 12.dp)
                .size(38.dp)
                .clip(CircleShape)
        )

        Column {
            Text(
                text = "Elsa Tomás",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            Text(
                text = "Adicionou uma nota há 2 horas",
                fontSize = 10.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
private fun PostOwnerPrev() {
    PostOwner()
}