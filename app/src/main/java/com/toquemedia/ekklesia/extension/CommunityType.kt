package com.toquemedia.ekklesia.extension

import com.toquemedia.ekklesia.model.CommunityWithMembers

fun List<CommunityWithMembers>.communitiesToJoin(userId: String): List<CommunityWithMembers> {
    return this.filter { communityWithMembers ->
        communityWithMembers.allMembers?.none { it.id == userId } != false
    }
}