package com.toquemedia.ekklesia.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.ProfileList
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: UserType,
    onNavigateToMyDevocional: () -> Unit = {},
    onSignOut: () -> Unit = {},
) {

    val profileList = listOf<ProfileList>(
        ProfileList.Devocional(
            title = stringResource(R.string.menu_devocional),
            icon = Icons.Rounded.AutoStories,
            onAction = onNavigateToMyDevocional
        ),
        ProfileList.Logout(
            title = stringResource(R.string.menu_logout),
            icon = Icons.AutoMirrored.Rounded.Logout,
            onAction = onSignOut
        )
    )

    Column {

        Spacer(modifier = Modifier.height(12.dp))

        PersonalInfo(
            user = user,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        )

        HorizontalDivider()

        Spacer(modifier = Modifier.height(10.dp))

        List(size = profileList.size) { index ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .clickable {
                        profileList[index].onAction()
                    }
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Image(
                    imageVector = profileList[index].icon,
                    contentDescription = profileList[index].title,
                    colorFilter = ColorFilter.tint(PrincipalColor)
                )

                Text(
                    text = profileList[index].title,
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.weight(weight = 1f))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileScreenPrev() {
    ProfileScreen(
        user = UserType(
            id = "7890",
            displayName = "Célio Garcia",
            email = "celio.garcia@celiogarcia.com",
            photo = "https://yt3.googleusercontent.com/qGrcViAdsmfdL8NhR03s6jZVi2AP4A03XeBFShu2M4Jd88k1fNXDnpMEmHU6CvNJuMyA2z1maA0=s900-c-k-c0x00ffffff-no-rj"
        )
    )
}