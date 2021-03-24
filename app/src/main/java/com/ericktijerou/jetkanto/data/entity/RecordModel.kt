package com.ericktijerou.jetkanto.data.entity


data class RecordModel(
    val user: UserModel,
    val songName: String,
    val videoUrl: String,
    val preview: String,
    val likeCount: Int
)
