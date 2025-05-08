package com.toquemedia.seedfy.ui.screens.bible.worship

import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.util.UnstableApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.toquemedia.seedfy.model.EkklesiaPlayer

@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VideoCreator(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    player: EkklesiaPlayer? = null,
    onCancelRecording: () -> Unit = {},
    onSaveRecording: (String) -> Unit = {},
    onDeleteRecord: (String) -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )

    if (permissionsState.allPermissionsGranted) {
        CameraPreviewScreen(
            context = context,
            player = player,
            lifecycleOwner = lifecycleOwner,
            onSaveRecording = onSaveRecording,
            onCancelRecording = onCancelRecording,
            onDeleteRecord = onDeleteRecord
        )
    } else {
        LaunchedEffect(Unit) {
            permissionsState.launchMultiplePermissionRequest()
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Câmera e áudio requerem a tua permissão para serem usadas.")
        }
    }
}