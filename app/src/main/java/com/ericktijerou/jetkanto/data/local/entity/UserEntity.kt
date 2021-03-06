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
package com.ericktijerou.jetkanto.data.local.entity

import com.ericktijerou.jetkanto.core.EMPTY
import com.ericktijerou.jetkanto.core.ZERO

data class UserEntity(
    val userId: String = EMPTY,
    val name: String,
    val username: String,
    val avatar: String,
    val bio: String = EMPTY,
    val followers: Int = ZERO,
    val followed: Int = ZERO,
    val views: Int = ZERO,
    val localAvatarPath: String = EMPTY
)
