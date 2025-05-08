package com.toquemedia.seedfy.ui.screens.community.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun InviteButton(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(color = PrincipalColor)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Link,
                contentDescription = stringResource(R.string.invite_link_description),
                tint = Color.White
            )
        }

        Column {
            Text(
                text = stringResource(R.string.invite_link),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrincipalColor
            )
            Text(
                text = stringResource(R.string.invite_link_tip),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun InviteButtonPrev() {
    InviteButton()
}