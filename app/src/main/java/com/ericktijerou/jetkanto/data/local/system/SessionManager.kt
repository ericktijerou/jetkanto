/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetkanto.data.local.system

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ericktijerou.jetkanto.core.EMPTY
import com.ericktijerou.jetkanto.core.ZERO
import com.ericktijerou.jetkanto.data.entity.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionManager @Inject constructor(private val context: Context) {

    private val Context.userDataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    val session: Flow<UserModel> = context.userDataStore.data
        .map { preferences ->
            val userId = preferences[USER_ID_KEY] ?: EMPTY
            val name = preferences[NAME_KEY] ?: EMPTY
            val username = preferences[USERNAME_KEY] ?: EMPTY
            val avatar = preferences[AVATAR_KEY] ?: EMPTY
            val bio = preferences[BIO_KEY] ?: EMPTY
            val followers = preferences[FOLLOWERS_KEY] ?: ZERO
            val followed = preferences[FOLLOWED_KEY] ?: ZERO
            val views = preferences[VIEWS_KEY] ?: ZERO
            UserModel(
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

    suspend fun saveSession(userModel: UserModel) = with(userModel) {
        context.userDataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
            preferences[NAME_KEY] = name
            preferences[USERNAME_KEY] = username
            preferences[AVATAR_KEY] = avatar
            preferences[BIO_KEY] = bio
            preferences[FOLLOWERS_KEY] = followers
            preferences[FOLLOWED_KEY] = followed
            preferences[VIEWS_KEY] = views
        }
    }

    companion object {
        private const val PREF_KEY_USER_ID = "user_id"
        private const val PREF_KEY_NAME = "name"
        private const val PREF_KEY_USERNAME = "username"
        private const val PREF_KEY_AVATAR = "avatar"
        private const val PREF_KEY_BIO = "bio"
        private const val PREF_KEY_FOLLOWERS = "followers"
        private const val PREF_KEY_FOLLOWED = "followed"
        private const val PREF_KEY_VIEWS = "views"
        private const val USER_PREFERENCES_NAME = "user_preferences"
        private val USER_ID_KEY = stringPreferencesKey(PREF_KEY_USER_ID)
        private val NAME_KEY = stringPreferencesKey(PREF_KEY_NAME)
        private val USERNAME_KEY = stringPreferencesKey(PREF_KEY_USERNAME)
        private val AVATAR_KEY = stringPreferencesKey(PREF_KEY_AVATAR)
        private val BIO_KEY = stringPreferencesKey(PREF_KEY_BIO)
        private val FOLLOWERS_KEY = intPreferencesKey(PREF_KEY_FOLLOWERS)
        private val FOLLOWED_KEY = intPreferencesKey(PREF_KEY_FOLLOWED)
        private val VIEWS_KEY = intPreferencesKey(PREF_KEY_VIEWS)
    }
}
