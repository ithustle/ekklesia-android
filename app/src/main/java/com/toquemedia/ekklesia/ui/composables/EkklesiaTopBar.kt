package com.toquemedia.ekklesia.ui.composables

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.getInitials
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EkklesiaTopBar(
    title: String,
    navigationBack: Boolean = true,
    showTitleAvatar: Boolean = false,
    userAvatar: Uri? = null,
    isBackgroundTransparent: Boolean = false,
    onNavigateToProfile: () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {}
) {

    val colors = if (isBackgroundTransparent) TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        titleContentColor = PrincipalColor
    ) else TopAppBarDefaults.topAppBarColors(
        containerColor = PrincipalColor,
        titleContentColor = Color.White
    )

    TopAppBar(
        windowInsets = TopAppBarDefaults.windowInsets,
        title = {
            Row {
                if (showTitleAvatar) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .background(color = PrincipalColor)
                            .size(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title.getInitials(),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    maxLines = 1,
                    color = PrincipalColor,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .width(256.dp)
                )
            }
        },
        navigationIcon = {
            if (navigationBack) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back_description),
                        tint = if (isBackgroundTransparent) PrincipalColor else Color.White
                    )
                }
            }
        },
        actions = {

            actions()

            if (userAvatar != null) {
                EkklesiaImage(
                    model = userAvatar,
                    contentDescription = stringResource(R.string.profileTitleScreen),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            onNavigateToProfile()
                        },
                )
            }
        },
        colors = colors,
    )
}

@Preview
@Composable
private fun EkklesiaTopBarPrev() {
    EkklesiaTopBar(
        title = "Título",
        isBackgroundTransparent = true,
        userAvatar = null,
        showTitleAvatar = true
    )
}