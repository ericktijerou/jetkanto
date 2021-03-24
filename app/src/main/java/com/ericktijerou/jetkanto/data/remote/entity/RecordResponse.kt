package com.ericktijerou.jetkanto.data.remote.entity

import com.ericktijerou.jetkanto.core.orZero
import com.ericktijerou.jetkanto.data.entity.RecordModel
import com.google.gson.annotations.SerializedName

data class RecordResponse(
    @SerializedName("user")
    val user: UserResponse? = null,

    @SerializedName("songName")
    val songName: String? = null,

    @SerializedName("record_video")
    val videoUrl: String? = null,

    @SerializedName("preview_img")
    val preview: String? = null,

    @SerializedName("likes")
    val likeCount: Int? = null
)

fun RecordResponse.toData() = RecordModel(
    user = user.orEmpty().toData(),
    songName = songName.orEmpty(),
    videoUrl = videoUrl.orEmpty(),
    preview = preview.orEmpty(),
    likeCount = likeCount.orZero()
)