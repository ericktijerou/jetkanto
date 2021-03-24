package com.ericktijerou.jetkanto.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import com.ericktijerou.jetkanto.data.local.dao.UserEntity

@Entity(tableName = "Record")
data class RecordEntity(
    @Embedded val address: UserEntity,
    val songName: String,
    val videoUrl: String,
    val preview: String,
    val likeCount: Int
)
