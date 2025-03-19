package com.toquemedia.ekklesia.ui.screens.community.chat

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage

@Composable
fun ChatRowHeaderInfo(
    modifier: Modifier = Modifier,
    communityName: String,
    communityImage: Uri?,

) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EkklesiaImage(
            model = communityImage,
            contentDescription = stringResource(R.string.community_description),
            modifier = modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Column {
            Text(
                text = communityName,
                fontSize = 18.sp
            )
            Text(
                text = "12 membros",
                fontSize = 12.sp,
                lineHeight = 15.sp,
                color = Color.LightGray
            )
        }
    }
}

@Preview
@Composable
private fun ChatRowHeaderInfoPrev() {
    ChatRowHeaderInfo(
        communityName = "Comunidade de Teste",
        communityImage = "https://picsum.photos/200/300".toUri()
    )
}