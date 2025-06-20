package com.toquemedia.seedfy

import android.net.Uri
import com.toquemedia.seedfy.model.BottomBarItem.Community
import com.toquemedia.seedfy.model.CommunityMemberType
import com.toquemedia.seedfy.model.CommunityWithMembers
import com.toquemedia.seedfy.model.UserType
import com.toquemedia.seedfy.services.CommunityService
import com.toquemedia.seedfy.services.NotificationService
import com.toquemedia.seedfy.services.StorageService
import com.toquemedia.seedfy.ui.screens.community.CommunityUiState
import com.toquemedia.seedfy.ui.screens.community.CommunityViewModel
import com.toquemedia.seedfy.utils.mocks.CommunityMock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.UUID

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

}

