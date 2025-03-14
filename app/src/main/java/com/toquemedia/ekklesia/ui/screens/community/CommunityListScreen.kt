package com.toquemedia.ekklesia.ui.screens.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.HeadTitle

@Composable
fun CommunityListScreen(
    modifier: Modifier = Modifier,
    onOpenToCreateCommunity: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 30.dp)
            .fillMaxSize()
    ) {
        HeadTitle(title = stringResource(R.string.community))

        Spacer(modifier = Modifier.height(24.dp))

        CommunityButtonAdd(
            onTapAction = onOpenToCreateCommunity
        )

        Spacer(modifier = Modifier.height(20.dp))

        List(5) {
            CommunityCard(
                communityName = "Minha comunidade",
                onTapAction = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CommunityListScreenPrev() {
    CommunityListScreen()
}