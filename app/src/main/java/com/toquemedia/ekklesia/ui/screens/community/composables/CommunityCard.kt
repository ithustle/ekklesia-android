package com.toquemedia.ekklesia.ui.screens.community.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.base64ToBitmap
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun CommunityCard(
    modifier: Modifier = Modifier,
    community: CommunityType,
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
        Image(
            bitmap = community.communityImage.base64ToBitmap().asImageBitmap(),
            contentDescription = stringResource(R.string.title_activity_main),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(54.dp)
                .clip(shape = RoundedCornerShape(4.dp)),
        )

        Column {
            Text(
                text = community.communityName,
                fontSize = 20.sp,
                color = PrincipalColor,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Membros: ${community.members}",
                fontSize = 13.sp,
                color = Color.DarkGray,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityCardPrev() {
    CommunityCard(
        community = CommunityMock.getAll().first()
    )
}