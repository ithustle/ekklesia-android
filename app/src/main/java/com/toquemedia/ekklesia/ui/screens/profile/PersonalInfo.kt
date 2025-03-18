package com.toquemedia.ekklesia.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.UserType
import com.toquemedia.ekklesia.ui.theme.PrincipalColor

@Composable
fun PersonalInfo(
    modifier: Modifier = Modifier,
    user: UserType
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.photo,
            contentDescription = stringResource(id = R.string.profile_image_description),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_background),
            modifier = Modifier
                .size(78.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)

        ) {
            Text(
                text = user.displayName.toString(),
                color = PrincipalColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp
            )
            Text(
                text = user.email.toString(),
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun PersonalInfoPrev() {
    PersonalInfo(
        user = UserType(
            id = "7890",
            displayName = "Célio Garcia",
            email = "celio.garcia@celiogarcia.com",
            photo = "https://assetsio.gnwcdn.com/dragon-ball-super-vegeta-liberta-a-sua-furia-1517751143067.jpg?width=1200&height=1200&fit=bounds&quality=70&format=jpg&auto=webp".toUri()
        ),
        modifier = Modifier
            .fillMaxWidth()
    )
}