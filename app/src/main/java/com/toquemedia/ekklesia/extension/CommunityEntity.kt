package com.toquemedia.ekklesia.extension

import android.content.Context
import android.net.Uri
import com.toquemedia.ekklesia.model.CommunityEntity
import com.toquemedia.ekklesia.model.CommunityType

fun CommunityEntity.toCommunity(context: Context): CommunityType {
    val uri = this.communityImage.base64ToBitmap().toUri(context).toString()
    val escapedUri = Uri.encode(uri)
    return CommunityType(
        id = this.id,
        communityName = this.communityName,
        communityDescription = this.communityDescription,
        members = this.members,
        communityImage = escapedUri,
        email = this.email
    )
}