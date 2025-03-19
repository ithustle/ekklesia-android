package com.toquemedia.ekklesia.ui.screens.community.chat

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    communityName: String,
    communityImage: Uri?
) {
    TopAppBar(
        windowInsets = TopAppBarDefaults.windowInsets,
        title = {
            ChatRowHeaderInfo(
                communityName = communityName,
                communityImage = communityImage
            )
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = "More",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PrincipalColor,
            titleContentColor = Color.White
        ),
    )
}

@Preview
@Composable
private fun ChatTopBarPrev() {
    ChatTopBar(
        communityName = "Comunidade de Estudos Bíblicos",
        communityImage = "https://picsum.photos/200/300".toUri()
    )
}