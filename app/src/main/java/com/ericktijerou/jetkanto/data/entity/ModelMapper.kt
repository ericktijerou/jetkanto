package com.ericktijerou.jetkanto.data.entity

import com.ericktijerou.jetkanto.data.local.entity.UserEntity
import com.ericktijerou.jetkanto.data.local.entity.RecordEntity
import com.ericktijerou.jetkanto.domain.entity.Record
import com.ericktijerou.jetkanto.domain.entity.User

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

fun RecordModel.toDomain() = Record(
    user = user.toDomain(),
    songName = songName,
    videoUrl = videoUrl,
    preview = preview,
    likeCount = likeCount
)

fun UserModel.toDomain() = User(id, name, username, avatar, bio, followers, followed, views)