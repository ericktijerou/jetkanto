package com.ericktijerou.jetkanto.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Record")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @Embedded val user: UserEntity,
    val songName: String,
    val videoUrl: String,
    val preview: String,
    val likeCount: Int
)
