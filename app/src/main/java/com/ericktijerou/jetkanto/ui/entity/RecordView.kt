package com.ericktijerou.jetkanto.ui.entity

import com.ericktijerou.jetkanto.domain.entity.User

data class RecordView(
    val songName: String,
    val videoUrl: String,
    val preview: String,
    val likeCount: Int,
    val ownerName: String,
    val ownerUsername: String,
    val ownerAvatar: String
)