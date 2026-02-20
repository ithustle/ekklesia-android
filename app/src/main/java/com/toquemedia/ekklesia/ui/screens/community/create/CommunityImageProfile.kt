package com.toquemedia.ekklesia.ui.screens.community.create

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun CommunityImageProfile(
    onChangeImage: () -> Unit,
    imageUri: Uri? = null
) {
    Row {
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(128.dp)
                .clip(shape = CircleShape)
                .background(color = Color.White)
                .clickable {
                    onChangeImage()
                }
        ) {
            if (imageUri == null) {
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    contentDescription = null,
                    tint = PrincipalColor,
                    modifier = Modifier
                        .size(48.dp)
                        .align(alignment = Alignment.Center)
                )
            } else {
                EkklesiaImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(128.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview
@Composable
private fun CommunityImageProfilePrev() {
    CommunityImageProfile({}, "".toUri())
}