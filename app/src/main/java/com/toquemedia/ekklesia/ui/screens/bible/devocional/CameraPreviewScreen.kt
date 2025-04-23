package com.toquemedia.ekklesia.ui.screens.bible.devocional

import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.RecordingState
import androidx.compose.ui.tooling.preview.Preview as PV

@Composable
fun CameraPreviewScreen(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onCancelRecording: () -> Unit = {},
    onSaveRecording: (Uri) -> Unit = {},
    onDeleteRecord: (String) -> Unit = {}
) {
    if (LocalInspectionMode.current) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier

                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = stringResource(R.string.close_camera),
                            tint = Color.White,
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                val recorder = Recorder.Builder().build()
                VideoRecordingControls(
                    context = context,
                    videoCapture = VideoCapture.withOutput(recorder),
                    recordingState = RecordingState.INIT,
                    onSwitchCamera = {},
                    onDeleteRecord = {},
                    onSaveRecording = {},
                    onStopRecording = {},
                    onStartRecording = {}
                )
            }
            Box(
                modifier = Modifier
                    .background(color = Color.Black)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Camera Preview", color = Color.White)
            }
        }
    } else {
        val recorder = remember {
            Recorder.Builder()
                .setQualitySelector(QualitySelector.from(Quality.HD))
                .build()
        }
        val videoCapture = remember { VideoCapture.withOutput(recorder) }
        var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_FRONT_CAMERA) }
        var recording by remember { mutableStateOf(RecordingState.INIT) }

        val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
        var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
        var previewView by remember { mutableStateOf<PreviewView?>(null) }

        LaunchedEffect(Unit) {
            cameraProviderFuture.addListener({
                try {
                    cameraProvider = cameraProviderFuture.get()
                } catch (e: Exception) {
                    println("Camera Falha ao obter ProcessCameraProvider: $e", )
                }
            }, ContextCompat.getMainExecutor(context))
        }

        DisposableEffect(Unit) {
            onDispose {
                cameraProvider?.unbindAll()
            }
        }

        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f),
            ) {

                if (recording != RecordingState.RECORDING) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        IconButton(
                            onClick = onCancelRecording,
                            modifier = Modifier

                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(R.string.close_camera),
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(Modifier.weight(1f))
                VideoRecordingControls(
                    recordingState = recording,
                    context = context,
                    videoCapture = videoCapture,
                    onSwitchCamera = {
                        cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                            CameraSelector.DEFAULT_FRONT_CAMERA
                        } else {
                            CameraSelector.DEFAULT_BACK_CAMERA
                        }
                    },
                    onDeleteRecord = {
                        recording = RecordingState.INIT
                        onDeleteRecord(it)
                    },
                    onSaveRecording = onSaveRecording,
                    onStopRecording = { recording = RecordingState.STOPPED },
                    onStartRecording = { recording = RecordingState.RECORDING }
                )
            }
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PreviewView(ctx).apply {
                        implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                    }.also { previewView = it }
                },
                update = { view ->
                    val provider = cameraProvider ?: return@AndroidView

                    try {
                        provider.unbindAll()

                        val preview = Preview.Builder().build().also {
                            it.surfaceProvider = view.surfaceProvider
                        }

                        provider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            videoCapture
                        )
                    } catch (e: Exception) {
                        println("Camera Falha ao vincular preview: $e")
                    }
                }
            )
        }
    }
}

@PV(showBackground = true)
@Composable
private fun CameraPreviewScreenPrev() {
    CameraPreviewScreen(
        context = LocalContext.current,
        lifecycleOwner = LocalLifecycleOwner.current
    )
}