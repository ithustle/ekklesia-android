package com.toquemedia.seedfy.ui.screens.community.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@Composable
fun CommunityButtonAdd(
    modifier: Modifier = Modifier,
    onTapAction: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onTapAction()
            }
    ) {
        Box(
            modifier = modifier
                .padding(end = 12.dp)
                .size(54.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .background(color = PrincipalColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Icone de grupo",
                tint = Color.White,
                modifier = Modifier
                    .size(38.dp)
            )
        }

        Text(
            text = stringResource(R.string.newCommunity),
            fontSize = 18.sp,
            color = PrincipalColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityButtonAddPrev() {
    CommunityButtonAdd()
}