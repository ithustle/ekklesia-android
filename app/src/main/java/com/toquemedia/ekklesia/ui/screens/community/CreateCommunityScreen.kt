package com.toquemedia.ekklesia.ui.screens.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.ui.composables.EkklesiaButton
import com.toquemedia.ekklesia.ui.composables.EkklesiaTextField
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCommunityScreen(modifier: Modifier = Modifier) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = PrincipalColor,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(stringResource(R.string.newCommunity))
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))
            CommunityImageProfile {}

            Spacer(Modifier.height(30.dp))

            EkklesiaTextField(
                value = "",
                placeholder = stringResource(R.string.community_title),
                height = 41.dp,
                singleLine = true,
                onChangeValue = { }
            )

            Spacer(Modifier.height(10.dp))

            EkklesiaTextField(
                value = "",
                placeholder = stringResource(R.string.community_description),
                height = 150.dp,
                onChangeValue = {

                }
            )

            Spacer(Modifier.weight(1f))

            EkklesiaButton(
                label = stringResource(R.string.save_community),
                modifier = Modifier
                    .fillMaxWidth()
            ) {

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateCommunityScreenPrev() {
    CreateCommunityScreen()
}