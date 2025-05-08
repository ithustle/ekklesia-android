package com.toquemedia.seedfy.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    icon: Painter,
    emptyText: String,
    emptyDescription: String? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(all = 16.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = emptyText,
            tint = PrincipalColor,
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = emptyText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = PrincipalColor,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(4.dp))

        emptyDescription?.let {
            Text(
                text = it,
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyScreenPrev() {
    EmptyScreen(
        icon = painterResource(R.drawable.comment),
        emptyText = stringResource(R.string.no_comments),
        emptyDescription = stringResource(R.string.no_comments_description),
        modifier = Modifier
            .fillMaxSize()
    )
}