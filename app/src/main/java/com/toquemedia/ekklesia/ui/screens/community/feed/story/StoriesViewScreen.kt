package com.toquemedia.ekklesia.ui.screens.community.feed.story

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toquemedia.ekklesia.extension.timeAgo
import com.toquemedia.ekklesia.model.StoryType
import com.toquemedia.ekklesia.ui.screens.community.feed.PostOwner
import com.toquemedia.ekklesia.utils.mocks.StoriesMock
import kotlinx.coroutines.delay

const val STORY_DURATION = 10000L

@Composable
fun StoriesViewScreen(
    stories: List<StoryType>,
    onFinishStory: () -> Unit = {}
) {

    var currentStoryIndex by remember { mutableIntStateOf(0) }
    var progress by remember { mutableFloatStateOf(0f) }
    var isPaused by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { stories.size }
    )

    LaunchedEffect(currentStoryIndex) {
        pagerState.animateScrollToPage(currentStoryIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != currentStoryIndex) {
            currentStoryIndex = pagerState.currentPage
            progress = 0f
        }
    }

    LaunchedEffect(currentStoryIndex, isPaused) {
        progress = 0f

        while (currentStoryIndex < stories.size && !isPaused) {
            val stepSize = 0.01f
            val delayTime = (STORY_DURATION * stepSize).toLong()

            delay(delayTime)
            progress += stepSize

            if (progress >= 1f) {
                if (currentStoryIndex < stories.size - 1) {
                    currentStoryIndex++
                    progress = 0f
                } else {
                    onFinishStory()
                    break
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
        //.horizontalScroll(state = rememberScrollState())
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val story = stories[page]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(story.backgroundColor))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPaused = true
                                awaitRelease()
                                isPaused = false
                            },
                            onTap = { offset ->
                                if (offset.x > size.width / 2 && currentStoryIndex < stories.size - 1) {
                                    currentStoryIndex++
                                } else if (offset.x <= size.width / 2 && currentStoryIndex > 0) {
                                    currentStoryIndex--
                                }
                            }
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PostOwner(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 30.dp),
                        user = story.user!!,
                        timeAgo = story.createdAt.timeAgo(),
                        color = Color.White,
                        postType = ""
                    )

                    Spacer(Modifier.weight(1f))

                    Text(
                        text = story.verse,
                        color = Color(story.verseColor),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 20.dp)
                    )

                    Text(
                        text = "- ${story.bookNameWithVersicle}",
                        color = Color(story.verseColor),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .padding(top = 20.dp)
                    )

                    Spacer(Modifier.weight(1f))
                }
            }
        }
        StoriesProgressIndicator(
            storiesCount = stories.size,
            currentStoryIndex = currentStoryIndex,
            currentProgress = progress,
            modifier = Modifier
                .padding(all = 16.dp)
        )
    }
}

@Preview
@Composable
private fun StoriesViewScreenPrev() {
    StoriesViewScreen(stories = StoriesMock.getAll())
}