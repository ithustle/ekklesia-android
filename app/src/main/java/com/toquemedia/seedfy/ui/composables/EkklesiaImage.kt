package com.toquemedia.seedfy.ui.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import com.toquemedia.seedfy.R

@Composable
fun EkklesiaImage(
    modifier: Modifier = Modifier,
    model: Uri?,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.Crop
) {
    val isPreview = LocalInspectionMode.current

    if (isPreview) {
        Image(
            painter = painterResource(R.drawable.avatar),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    } else {
        val painter = rememberAsyncImagePainter(
            model = model,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentScale = contentScale,
        )

        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun EkklesiaImagePrev() {
    EkklesiaImage(
        model = "https://www.wikihow.com/images/thumb/9/9e/Group-Apps-on-Android-Step-9-Version-3.jpg/v4-460px-Group-Apps-on-Android-Step-9-Version-3.jpg".toUri(),
        contentDescription = stringResource(id = R.string.profile_image_description),
        modifier = Modifier
            .size(78.dp)
            .clip(CircleShape)
    )
}