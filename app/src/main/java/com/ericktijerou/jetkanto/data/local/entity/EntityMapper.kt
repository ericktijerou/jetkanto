package com.ericktijerou.jetkanto.data.local.entity

import com.ericktijerou.jetkanto.data.entity.RecordModel
import com.ericktijerou.jetkanto.data.entity.UserModel

fun UserEntity.toData() = UserModel(
    id = id,
    name = name,
    username = username,
    avatar = avatar,
    bio = bio,
    followers = followers,
    followed = followed,
    views = views
)

fun RecordEntity.toData() = RecordModel(
    user = user.toData(),
    songName = songName,
    videoUrl = videoUrl,
    preview = preview,
    likeCount = likeCount
)