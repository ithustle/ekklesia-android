package com.toquemedia.ekklesia.ui.screens.community.create

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.toquemedia.ekklesia.R
import com.toquemedia.ekklesia.model.ValidationResult
import com.toquemedia.ekklesia.ui.composables.EkklesiaButton
import com.toquemedia.ekklesia.ui.composables.EkklesiaTextField
import com.toquemedia.ekklesia.ui.screens.community.CommunityUiState
import com.toquemedia.ekklesia.ui.theme.PrincipalColor
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCommunityScreen(
    modifier: Modifier = Modifier,
    onTapToLoadImageOnLibrary: () -> Unit = {},
    onCreateCommunity: () -> Unit = {},
    onCreateSuccessfulCommunity: (Boolean) -> Unit,
    state: CommunityUiState = CommunityUiState(),
    validation: SharedFlow<ValidationResult>
) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        validation.collect { result ->
            when(result) {
                is ValidationResult.Success -> {
                    isLoading = false
                    onCreateSuccessfulCommunity(true)
                    Toast.makeText(context, "Comunidade criada com sucesso.", Toast.LENGTH_SHORT).show()
                }
                is ValidationResult.Error -> {
                    isLoading = false
                    Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                }
                is ValidationResult.Loading -> {
                    isLoading = true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(30.dp))
        CommunityImageProfile(
            imageUri = state.imageUri,
            onChangeImage = onTapToLoadImageOnLibrary
        )

        Spacer(Modifier.height(30.dp))

        EkklesiaTextField(
            value = state.communityName,
            placeholder = stringResource(R.string.community_title),
            height = 41.dp,
            singleLine = true,
            onChangeValue = {
                state.onCommunityNameChange(it.text)
            },
            enabled = !isLoading
        )

        Spacer(Modifier.height(10.dp))

        EkklesiaTextField(
            value = state.communityDescription,
            placeholder = stringResource(R.string.community_description),
            height = 150.dp,
            onChangeValue = {
                state.onCommunityDescriptionChange(it.text)
            },
            enabled = !isLoading
        )

        Spacer(Modifier.weight(1f))

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(24.dp),
                color = PrincipalColor
            )
        } else {
            EkklesiaButton(
                label = stringResource(R.string.save_community),
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                onCreateCommunity()
            }
        }
    }
}