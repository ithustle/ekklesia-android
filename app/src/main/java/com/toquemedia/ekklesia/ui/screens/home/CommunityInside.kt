package com.toquemedia.ekklesia.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.extension.toCommunity
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.ui.composables.EkklesiaImage
import com.toquemedia.ekklesia.utils.mocks.CommunityMock

@Composable
fun CommunityInside(
    modifier: Modifier = Modifier,
    community: CommunityType,
    onNavigateToCommunity: (CommunityType) -> Unit = {}
) {
    Column(
        modifier = modifier
            .width(width = 96.dp)
            .clickable {
                onNavigateToCommunity(community)
            }
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(all = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EkklesiaImage(
            model = community.communityImage.toUri(),
            contentDescription = stringResource(R.string.community_description),
            modifier = Modifier
                .size(84.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = community.communityName,
            fontSize = 10.sp,
            maxLines = 2,
            lineHeight = 10.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun CommunityInsidePrev() {
    val context = LocalContext.current
    CommunityInside(community = CommunityMock.getAll().first().toCommunity(context))
}