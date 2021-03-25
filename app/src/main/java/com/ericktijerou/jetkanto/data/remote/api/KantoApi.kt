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
package com.ericktijerou.jetkanto.data.remote.api

import com.ericktijerou.jetkanto.data.remote.entity.RecordResponse
import com.ericktijerou.jetkanto.data.remote.entity.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface KantoApi {
    @GET("v3/4efa83dd-6ff7-4bc1-9c17-3a45016978a7")
    suspend fun getUserInfo(): Response<UserResponse>

    @GET("v3/2f188654-7f58-4267-8887-ede536d8382e")
    suspend fun getRecordList(): Response<List<RecordResponse>>
}
