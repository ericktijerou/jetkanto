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
package com.ericktijerou.jetkanto.ui.util

import com.ericktijerou.jetkanto.ui.entity.RecordView
import com.ericktijerou.jetkanto.ui.entity.UserView

object MockDataHelper {
    val session by lazy {
        UserView(
            id = "123456",
            name = "Erick Tijero",
            username = "ericktijerou",
            avatar = "",
            bio = "This is mi biography",
            followers = 23,
            followed = 12,
            views = 21,
            localAvatarPath = ""
        )
    }

    val record by lazy {
        RecordView(
            id = 1L,
            songName = "Songname",
            videoUrl = "",
            preview = "",
            likeCount = 10,
            ownerName = "Owner",
            ownerUsername = "username",
            ownerAvatar = "",
            isFavorite = true
        )
    }
}
