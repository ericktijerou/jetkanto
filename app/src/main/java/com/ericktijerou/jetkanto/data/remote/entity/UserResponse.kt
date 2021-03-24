package com.ericktijerou.jetkanto.data.remote.entity

import com.ericktijerou.jetkanto.core.EMPTY
import com.ericktijerou.jetkanto.core.ZERO
import com.ericktijerou.jetkanto.core.orZero
import com.ericktijerou.jetkanto.data.entity.UserModel
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