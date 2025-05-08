package com.toquemedia.seedfy.ui.screens.bible.worship

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    initialColor: Color,
    onColorChanged: (Color) -> Unit = {}
) {
    val controller = rememberColorPickerController()

    LaunchedEffect(initialColor) {
        controller.selectByColor(initialColor, true)
    }

    Box(
        modifier = modifier
            .width(164.dp)
            .height(164.dp)
    ) {
        HsvColorPicker(
            modifier = modifier,
            initialColor = initialColor,
            controller = controller,
            onColorChanged = { colorEnvelope ->
                onColorChanged(colorEnvelope.color)
            }
        )
    }
}

@Preview
@Composable
private fun ColorPickerPrev() {
    ColorPicker(initialColor = PrincipalColor)
}