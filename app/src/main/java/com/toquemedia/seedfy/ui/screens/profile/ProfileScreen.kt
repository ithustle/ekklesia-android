package com.toquemedia.seedfy.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Book
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.model.ProfileList
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.ui.theme.PrincipalColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: UserType,
    myNote: String = "null",
    onChangeMyNote: (String) -> Unit = {},
    onNavigateToMyDevocional: () -> Unit = {},
    onSaveNote: (String) -> Unit = {},
    onSignOut: () -> Unit = {},
) {

    var showInput by remember { mutableStateOf(false) }

    val profileList = listOf<ProfileList>(
        ProfileList.Biography(
            title = stringResource(R.string.my_notes),
            icon = Icons.Rounded.EditNote,
            onAction = onNavigateToMyDevocional
        ),
        ProfileList.Devocional(
            title = stringResource(R.string.menu_devocional),
            icon = Icons.Rounded.AutoStories,
            onAction = onNavigateToMyDevocional
        ),
        ProfileList.Devocional(
            title = stringResource(R.string.menu_bible_study),
            icon = Icons.Rounded.Book,
            onAction = onNavigateToMyDevocional
        ),
        ProfileList.Logout(
            title = stringResource(R.string.menu_logout),
            icon = Icons.AutoMirrored.Rounded.Logout,
            onAction = onSignOut
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            PrincipalColor,
                            PrincipalColor.copy(alpha = 0.8f)
                        )
                    )
                )
        ) {
            PersonalInfo(
                user = user,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 24.dp)
            )
        }

        // Lista de opções com cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-16).dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(top = 24.dp)
        ) {
            List(size = profileList.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable {
                            if (profileList[index].title != "Minhas notas") {
                                profileList[index].onAction()
                            }
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (index == profileList.lastIndex)
                            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (index == profileList.lastIndex)
                                        MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                                    else PrincipalColor.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = profileList[index].icon,
                                contentDescription = profileList[index].title,
                                tint = if (index == profileList.lastIndex)
                                    MaterialTheme.colorScheme.error
                                else PrincipalColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            if (profileList[index].title == stringResource(R.string.my_notes)) {
                                if (showInput) {
                                    TextField(
                                        value = if (myNote == "null") "" else myNote,
                                        onValueChange = onChangeMyNote,
                                        textStyle = LocalTextStyle.current.copy(
                                            textAlign = TextAlign.Left,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        ),
                                        placeholder = {
                                            Text(
                                                text = "Digite sua nota...",
                                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                                fontSize = 16.sp
                                            )
                                        },
                                        singleLine = true,
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = Color.Transparent,
                                            unfocusedContainerColor = Color.Transparent,
                                            focusedIndicatorColor = PrincipalColor,
                                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                            disabledIndicatorColor = Color.Transparent,
                                            cursorColor = PrincipalColor
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Done,
                                            capitalization = KeyboardCapitalization.Sentences,
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onDone = {
                                                showInput = false
                                                onSaveNote(myNote)
                                            }
                                        )
                                    )
                                } else {
                                    Text(
                                        text = profileList[index].title,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (myNote != "null") {
                                        Text(
                                            text = myNote,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(top = 2.dp)
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = profileList[index].title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (index == profileList.lastIndex)
                                        MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        if (profileList[index].title == stringResource(R.string.my_notes)) {
                            IconButton(
                                onClick = {
                                    if (showInput) {
                                        showInput = false
                                        onSaveNote(myNote)
                                    } else {
                                        showInput = true
                                    }
                                },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (showInput) Icons.Rounded.Check else Icons.Rounded.Edit,
                                    contentDescription = null,
                                    tint = PrincipalColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        } else if (index != profileList.lastIndex) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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