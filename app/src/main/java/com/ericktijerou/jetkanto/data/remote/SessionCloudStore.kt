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