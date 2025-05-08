package com.toquemedia.seedfy.extension

import com.toquemedia.seedfy.model.CommunityWithMembers

fun List<CommunityWithMembers>.communitiesToJoin(userId: String): List<CommunityWithMembers> {
    return this.filter { communityWithMembers ->
        communityWithMembers.allMembers.none { it.id == userId } != false
    }
}