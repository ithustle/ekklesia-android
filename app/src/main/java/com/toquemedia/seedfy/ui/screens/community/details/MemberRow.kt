package com.toquemedia.seedfy.ui.screens.community.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.ui.composables.EkklesiaImage
import com.toquemedia.seedfy.utils.mocks.CommunityMock

@Composable
fun MemberRow(
    modifier: Modifier = Modifier,
    member: CommunityMemberType,
    userQuote: String? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EkklesiaImage(
            model = member.user.photo?.toUri(),
            contentDescription = stringResource(R.string.profile_image_description),
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
        )

        Column(
            modifier = Modifier
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = member.user.displayName.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            userQuote?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    fontSize = 11.sp
                )
            }
        }

        Spacer(Modifier.weight(1f))

        if (member.admin) {
            Text(
                text = "Admin",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MemberRowPrev() {
    MemberRow(
        modifier = Modifier
            .fillMaxWidth(),
        member = CommunityMock.getAllCommunityWithMembers()[0].allMembers.first(),
        userQuote = "Bem sei eu que tudo podes e os Teus planos não podem ser frustrados"
    )
}