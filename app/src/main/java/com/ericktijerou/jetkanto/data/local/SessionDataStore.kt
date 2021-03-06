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
package com.ericktijerou.jetkanto.data.local

import com.ericktijerou.jetkanto.data.entity.UserModel
import com.ericktijerou.jetkanto.data.local.system.PreferenceHelper
import com.ericktijerou.jetkanto.data.local.system.SessionHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SessionDataStore @Inject constructor(
    private val sessionHelper: SessionHelper,
    private val preferenceHelper: PreferenceHelper
) {
    fun getSession(): Flow<UserModel> = sessionHelper.session

    suspend fun saveSession(newSession: UserModel) {
        sessionHelper.saveSession(newSession)
    }

    fun getUiMode(): Flow<Boolean> = preferenceHelper.uiModeFlow

    suspend fun setDarkMode(enable: Boolean) {
        preferenceHelper.setDarkMode(enable)
    }
}
