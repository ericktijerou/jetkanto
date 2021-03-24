package com.ericktijerou.jetkanto.data.entity

data class UserModel(
    val id: String,
    val name: String,
    val username: String,
    val avatar: String,
    val bio: String,
    val followers: Int,
    val followed: Int,
    val views: Int
)