package com.toquemedia.ekklesia.model

enum class MessageStatus(val status: String) {
    SENT("sent"),
    DELIVERED("delivered"),
    READ("read")
}