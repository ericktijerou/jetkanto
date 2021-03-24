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