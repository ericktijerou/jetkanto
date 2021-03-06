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
package com.ericktijerou.jetkanto.ui.entity

import com.ericktijerou.jetkanto.core.EMPTY
import com.ericktijerou.jetkanto.core.ZERO
import com.ericktijerou.jetkanto.domain.entity.Record
import com.ericktijerou.jetkanto.domain.entity.User

fun User.toView() = UserView(id, name, username, avatar, bio, followers, followed, views, localAvatarPath)

fun UserView?.orEmpty(): UserView {
    return this ?: UserView(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ZERO, ZERO, ZERO, EMPTY)
}

fun Record.toView() = RecordView(
    id = id,
    ownerName = user.name,
    ownerUsername = user.username,
    ownerAvatar = user.avatar,
    songName = songName,
    videoUrl = videoUrl,
    preview = preview,
    likeCount = likeCount,
    isFavorite = isFavorite
)

fun UserView.toDomain() = User(
    id = id,
    name = name,
    username = username,
    avatar = avatar,
    bio = bio,
    followers = followers,
    followed = followed,
    views = views,
    localAvatarPath = localAvatarPath
)
