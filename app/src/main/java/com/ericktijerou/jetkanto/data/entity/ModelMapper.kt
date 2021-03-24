package com.ericktijerou.jetkanto.data.entity

import com.ericktijerou.jetkanto.data.local.entity.UserEntity
import com.ericktijerou.jetkanto.data.local.entity.RecordEntity

fun RecordModel.toLocal() = RecordEntity(
    user = user.toLocal(),
    songName = songName,
    videoUrl = videoUrl,
    preview = preview,
    likeCount = likeCount
)

fun UserModel.toLocal() = UserEntity(
    name = name,
    username = username,
    avatar = avatar
)