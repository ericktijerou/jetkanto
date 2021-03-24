package com.ericktijerou.jetkanto.data.local.entity

import com.ericktijerou.jetkanto.core.EMPTY
import com.ericktijerou.jetkanto.core.ZERO

data class UserEntity(
    val id: String = EMPTY,
    val name: String,
    val username: String,
    val avatar: String,
    val bio: String = EMPTY,
    val followers: Int = ZERO,
    val followed: Int = ZERO,
    val views: Int = ZERO
)