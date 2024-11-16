package com.toquemedia.ekklesia.ui.screens.community

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun CommunityCard(
    modifier: Modifier = Modifier,
    communityName: String,
    numberOfMembers: Int = 0,
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
        AsyncImage(
            model = "https://img.freepik.com/free-vector/label-with-people-community-social-message_24877-53863.jpg",
            placeholder = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.title_activity_main),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(54.dp)
                .clip(shape = RoundedCornerShape(4.dp)),
        )

        Column {
            Text(
                text = communityName,
                fontSize = 20.sp,
                color = PrincipalColor,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Membros: $numberOfMembers",
                fontSize = 13.sp,
                color = Color.DarkGray,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityCardPrev() {
    CommunityCard(communityName = "Minha comunidade")
}