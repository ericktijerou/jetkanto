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
package com.ericktijerou.jetkanto.data.remote

import com.ericktijerou.jetkanto.core.NetworkConnectivity
import com.ericktijerou.jetkanto.core.NetworkException
import com.ericktijerou.jetkanto.core.NotFoundException
import com.ericktijerou.jetkanto.data.entity.UserModel
import com.ericktijerou.jetkanto.data.remote.api.KantoApi
import com.ericktijerou.jetkanto.data.remote.entity.toData
import javax.inject.Inject

class SessionCloudStore @Inject constructor(
    private val api: KantoApi,
    private val networkConnectivity: NetworkConnectivity
) {
    suspend fun getUserInfo(): UserModel {
        return if (networkConnectivity.isInternetOn()) {
            api.getUserInfo().body()?.toData() ?: throw NotFoundException()
        } else {
            throw NetworkException()
        }
    }
}
