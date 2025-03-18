package com.toquemedia.ekklesia.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.ui.theme.backgroundLightColor

@Composable
fun ProfileScreen(
    user: UserType,
    onSignOut: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundLightColor)
            .padding(all = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(14.dp))

        PersonalInfo(
            user = user,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        List(size = 1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .clickable {
                        onSignOut()
                    }
                    .padding(vertical = 12.dp)
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Rounded.Logout,
                    contentDescription = "Logout"
                )

                Text(
                    text = stringResource(R.string.menu_logout),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
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
            photo = "https://yt3.googleusercontent.com/qGrcViAdsmfdL8NhR03s6jZVi2AP4A03XeBFShu2M4Jd88k1fNXDnpMEmHU6CvNJuMyA2z1maA0=s900-c-k-c0x00ffffff-no-rj".toUri()
        )
    )
}