package com.toquemedia.seedfy.ui.screens.community.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.utils.mocks.CommunityMock

@Composable
fun Members(
    modifier: Modifier = Modifier,
    members: List<CommunityMemberType>,
    currentUser: UserType? = null
) {

    val sortedMembers = members.sortedWith(
        comparator = compareBy<CommunityMemberType> { it.id != currentUser?.id }
            .thenByDescending { it.admin }
            .thenBy { it.user.displayName?.lowercase() ?: "" }
    )

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp),
        userScrollEnabled = false
    ) {

        item {
            Text(
                text = "${if (members.size > 1) members.size else ""} ${stringResource(R.string.members)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )
        }

        items(sortedMembers) {
            MemberRow(
                member = it,
                modifier = Modifier
                    .padding(vertical = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MembersPrev() {
    Members(
        modifier = Modifier
            .padding(16.dp),
        members = CommunityMock.getAllCommunityWithMembers()[0].allMembers
    )
}