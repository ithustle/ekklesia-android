package com.toquemedia.seedfy.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.seedfy.R
import com.toquemedia.seedfy.ui.composables.EkklesiaButton
import com.toquemedia.seedfy.ui.theme.PrincipalColor

object OnboardingImages {
    val SEED = R.drawable.seedfy_logo
    val COMMUNITY = R.drawable.community
    val STORIES = R.drawable.story_onboarding
}

data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnboardingPage(
        imageRes = OnboardingImages.SEED,
        title = "Plante Sua Fé",
        description = "Leia e estude a Bíblia, com acesso offline a milhares de versículos."
    ),
    OnboardingPage(
        imageRes = OnboardingImages.COMMUNITY,
        title = "Cresça em Comunidade",
        description = "Junte-se as comunidades do Seedfy para compartilhar reflexões e conectar com outros fiéis."
    ),
    OnboardingPage(
        imageRes = OnboardingImages.STORIES,
        title = "Espalhe o Evangelho",
        description = "Crie devocionais e stories com para inspirar o mundo."
    )
)

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    isLast: Boolean,
    onNext: () -> Unit,
    onSkip: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0x4F9B3A6A),
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (!isLast) {
                TextButton(
                    onClick = onSkip,
                    modifier = Modifier
                        .align(Alignment.End)
                ) {

                }
            } else {
                Spacer(modifier = Modifier.height(32.dp))
            }

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = page.imageRes),
                    contentDescription = page.title,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 32.dp)
                )
                Text(
                    text = page.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrincipalColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = page.description,
                    fontSize = 16.sp,
                    color = PrincipalColor.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(onboardingPages.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = if (onboardingPages[index] == page) PrincipalColor else Color.White.copy(
                                        alpha = 0.4f
                                    ),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }

                EkklesiaButton(
                    label = if (isLast) "Começar" else "Próximo",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNext
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingPagePrev() {
    OnboardingPageContent(
        page = onboardingPages[2],
        isLast = false,
        onNext = {},
        onSkip = {}
    )
}