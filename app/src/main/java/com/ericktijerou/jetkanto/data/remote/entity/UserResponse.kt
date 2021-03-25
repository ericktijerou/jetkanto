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
package com.ericktijerou.jetkanto.data.remote.entity

import com.ericktijerou.jetkanto.core.EMPTY
import com.ericktijerou.jetkanto.core.ZERO
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("user_id_encrypted")
    val id: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("user_name")
    val username: String? = null,

    @SerializedName("profilePicture")
    val avatar: String? = null,

    @SerializedName("biography")
    val bio: String? = null,

    @SerializedName("followers")
    val followers: Int? = null,

    @SerializedName("followed")
    val followed: Int? = null,

    @SerializedName("views")
    val views: Int? = null
)

fun UserResponse?.orEmpty(): UserResponse {
    return this ?: UserResponse(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, ZERO, ZERO, ZERO)
}
