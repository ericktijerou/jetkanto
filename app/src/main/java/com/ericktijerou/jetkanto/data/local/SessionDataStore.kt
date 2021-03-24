package com.ericktijerou.jetkanto.data.local

import com.ericktijerou.jetkanto.data.entity.UserModel
import com.ericktijerou.jetkanto.data.local.system.SessionManager
import javax.inject.Inject

class SessionDataStore @Inject constructor(
    private val sessionManager: SessionManager
) {
    fun getSession(): UserModel = with(sessionManager) {
        return UserModel(
            id = userId,
            name = name,
            username = username,
            avatar = avatar,
            bio = bio,
            followers = followers,
            followed = followed,
            views = views
        )
    }

    fun saveSession(newSession: UserModel) {
        with(sessionManager) {
            userId = newSession.id
            name = newSession.name
            username = newSession.username
            avatar = newSession.avatar
            bio = newSession.bio
            followers = newSession.followers
            followed = newSession.followed
            views = newSession.views
        }
    }
}