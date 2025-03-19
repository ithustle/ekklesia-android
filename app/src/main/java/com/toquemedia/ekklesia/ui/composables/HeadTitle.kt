package com.toquemedia.ekklesia.ui.composables

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun HeadTitle(
    photo: Uri?,
    title: String,
    onNavigateToProfile: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrincipalColor
        )

        Spacer(modifier = Modifier.weight(1f))

        EkklesiaImage(
            model = photo,
            contentDescription = stringResource(R.string.profileTitleScreen),
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .clickable {
                    onNavigateToProfile()
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HeadTitlePrev() {
    HeadTitle(
        photo = "".toUri(),
        title = "Comunidades"
    )
}