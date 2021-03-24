package com.ericktijerou.jetkanto.data.remote.entity

import com.ericktijerou.jetkanto.core.orZero
import com.ericktijerou.jetkanto.data.entity.RecordModel
import com.ericktijerou.jetkanto.data.entity.UserModel

fun RecordResponse.toData() = RecordModel(
    user = user.orEmpty().toData(),
    songName = songName.orEmpty(),
    videoUrl = videoUrl.orEmpty(),
    preview = preview.orEmpty(),
    likeCount = likeCount.orZero()
)

fun UserResponse.toData() = UserModel(
    id = id.orEmpty(),
    name = name.orEmpty(),
    username = username.orEmpty(),
    avatar = avatar.orEmpty(),
    bio = bio.orEmpty(),
    followers = followers.orZero(),
    followed = followed.orZero(),
    views = views.orZero()
)
