package com.ericktijerou.jetkanto.data.local.system

import android.content.Context
import android.content.SharedPreferences
import com.ericktijerou.jetkanto.core.EMPTY
import javax.inject.Inject

class SessionManager @Inject constructor(context: Context) {

    companion object {
        private const val PREF_PACKAGE_NAME = "com.ericktijerou.jetkanto.preferences"
        private const val PREF_KEY_USER_ID = "user_id"
        private const val PREF_KEY_NAME = "name"
        private const val PREF_KEY_USERNAME = "username"
        private const val PREF_KEY_AVATAR = "avatar"
        private const val PREF_KEY_BIO = "bio"
        private const val PREF_KEY_FOLLOWERS = "followers"
        private const val PREF_KEY_FOLLOWED = "followed"
        private const val PREF_KEY_VIEWS = "views"
    }

    private val pref: SharedPreferences = context.getSharedPreferences(PREF_PACKAGE_NAME, Context.MODE_PRIVATE)

    var userId: String
        get() = pref.getString(PREF_KEY_USER_ID, EMPTY).orEmpty()
        set(userId) = pref.edit().putString(PREF_KEY_USER_ID, userId).apply()

    var name: String
        get() = pref.getString(PREF_KEY_NAME, EMPTY).orEmpty()
        set(name) = pref.edit().putString(PREF_KEY_NAME, name).apply()

    var username: String
        get() = pref.getString(PREF_KEY_USERNAME, EMPTY).orEmpty()
        set(username) = pref.edit().putString(PREF_KEY_USERNAME, username).apply()

    var avatar: String
        get() = pref.getString(PREF_KEY_AVATAR, EMPTY).orEmpty()
        set(avatar) = pref.edit().putString(PREF_KEY_AVATAR, avatar).apply()

    var bio: String
        get() = pref.getString(PREF_KEY_BIO, EMPTY).orEmpty()
        set(bio) = pref.edit().putString(PREF_KEY_BIO, bio).apply()

    var followers: Int
        get() = pref.getInt(PREF_KEY_FOLLOWERS, 0)
        set(followers) = pref.edit().putInt(PREF_KEY_FOLLOWERS, followers).apply()

    var followed: Int
        get() = pref.getInt(PREF_KEY_FOLLOWED, 0)
        set(followed) = pref.edit().putInt(PREF_KEY_FOLLOWED, followed).apply()

    var views: Int
        get() = pref.getInt(PREF_KEY_VIEWS, 0)
        set(views) = pref.edit().putInt(PREF_KEY_VIEWS, views).apply()
}