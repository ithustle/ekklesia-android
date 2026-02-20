package com.toquemedia.ekklesia.ui.screens.bible.worship

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cameraswitch
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.FiberManualRecord
import androidx.compose.material.icons.rounded.StopCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.formatTime
import com.toquemedia.ekklesia.model.RecordingState
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun VideoRecordingControls(
    modifier: Modifier = Modifier,
    context: Context,
    recordingState: RecordingState = RecordingState.INIT,
    videoCapture: VideoCapture<Recorder>,
    onSwitchCamera: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: (Uri?) -> Unit,
    onSaveRecording: (String) -> Unit = { _ -> },
    onDeleteRecord: (String) -> Unit,
) {

    var isRecording by remember { mutableStateOf(false) }
    var recording by remember { mutableStateOf<Recording?>(null) }
    var totalSeconds by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var timing by remember { mutableStateOf("00:00") }
    var videoPath by remember { mutableStateOf<String?>(null) }

    fun stopRecording() {
        isRunning = false
        recording?.stop()
        recording = null
    }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (true) {
                delay(1000)
                totalSeconds++
                timing = totalSeconds.formatTime()
            }

            if (totalSeconds >= 120) {
                stopRecording()
            }
        }
    }

    fun handleRecordingVideo() {
        when (recordingState) {
            RecordingState.RECORDING -> {
                stopRecording()
            }

            RecordingState.INIT -> {
                isRunning = true
                totalSeconds = 0

                val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
                val outputDir = context.getExternalFilesDir(null)
                    ?: context.filesDir
                val videoFile = File(outputDir, "VIDEO_$timeStamp.mp4")

                val outputOptions = FileOutputOptions.Builder(videoFile).build()

                recording = videoCapture.output
                    .prepareRecording(context, outputOptions)
                    .apply {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            withAudioEnabled()
                        }
                    }
                    .start(ContextCompat.getMainExecutor(context)) { event ->
                        when (event) {
                            is VideoRecordEvent.Start -> {
                                isRecording = true
                            }

                            is VideoRecordEvent.Finalize -> {
                                isRecording = false
                                recording = null
                                if (event.hasError()) {
                                    println("Gravação falhou: ${event.error} - ${event.cause?.message}")
                                } else {
                                    videoPath = videoFile.absolutePath
                                }
                                onStopRecording(event.outputResults.outputUri)
                            }
                        }
                    }

                onStartRecording()
            }

            RecordingState.STOPPED -> {
                isRunning = false
                timing = "00:00"
                totalSeconds = 0

                videoPath?.let {
                    onDeleteRecord(it)
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "$timing / 02:00",
            color = Color.White
        )

        Row(
            modifier = modifier
                .height(64.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSwitchCamera) {
                Icon(
                    imageVector = Icons.Rounded.Cameraswitch,
                    contentDescription = stringResource(R.string.switch_camera),
                    tint = Color.White,
                )
            }

            IconButton(
                onClick = {
                    handleRecordingVideo()
                },
                modifier = Modifier
                    .fillMaxHeight()
                    .width(width = 64.dp)
            ) {

                Icon(
                    imageVector = when (recordingState) {
                        RecordingState.RECORDING -> Icons.Rounded.StopCircle
                        RecordingState.INIT -> Icons.Rounded.FiberManualRecord
                        RecordingState.STOPPED -> Icons.Rounded.Delete
                    },
                    contentDescription = stringResource(R.string.recording),
                    tint = Color.Red,
                    modifier = Modifier
                        .size(size = 64.dp)
                )
            }

            if (recordingState == RecordingState.STOPPED) {
                IconButton(
                    onClick = {
                        videoPath?.let {
                            onSaveRecording(it)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Done,
                        contentDescription = stringResource(R.string.save_record),
                        tint = Color.White,
                        modifier = Modifier
                            .size(size = 28.dp)
                    )
                }
            } else {
                Box(modifier = Modifier.size(size = 28.dp))
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun VideoRecordingControlsPrev() {
    val recorder = Recorder.Builder().build()
    VideoRecordingControls(
        context = LocalContext.current,
        videoCapture = VideoCapture.withOutput(recorder),
        onSwitchCamera = {},
        onStartRecording = {},
        onStopRecording = {},
        onDeleteRecord = {}
    )
}