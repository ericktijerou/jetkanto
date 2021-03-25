package com.ericktijerou.jetkanto.domain.entity

data class Record(
    val user: User,
    val songName: String,
    val videoUrl: String,
    val preview: String,
    val likeCount: Int
)