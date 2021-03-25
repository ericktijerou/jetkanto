/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetkanto.data.entity

import com.ericktijerou.jetkanto.data.local.entity.RecordEntity
import com.ericktijerou.jetkanto.data.local.entity.UserEntity
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
