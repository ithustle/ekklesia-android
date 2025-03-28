package com.toquemedia.ekklesia.extension

import android.content.Context
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityType
import com.toquemedia.ekklesia.model.CommunityWithMembers

fun CommunityEntity.toCommunity(context: Context): CommunityType {
    val uri = this.communityImage.base64ToBitmap().toUri(context).toString()
    return CommunityType(
        id = this.id,
        communityName = this.communityName,
        communityDescription = this.communityDescription,
        members = this.members,
        communityImage = uri,
        email = this.email
    )
}

fun List<CommunityWithMembers>.communitiesToJoin(userId: String): List<CommunityWithMembers> {
    return this.filter { communityWithMembers ->
        communityWithMembers.allMembers?.none { it.id == userId } != false
    }
}

fun List<CommunityEntity>.toCommunity(context: Context): List<CommunityType> {

    var communities: MutableList<CommunityType> = mutableListOf()

    for (community in this) {
        val uri = community.communityImage.base64ToBitmap().toUri(context).toString()

        communities.add(CommunityType(
            id = community.id,
            communityName = community.communityName,
            communityDescription = community.communityDescription,
            members = community.members,
            communityImage = uri,
            email = community.email
        ))
    }

    return communities
}