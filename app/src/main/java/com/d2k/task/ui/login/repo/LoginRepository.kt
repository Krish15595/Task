package com.d2k.task.ui.login.repo


import com.d2k.task.ui.login.model.LoginRequest
import com.d2k.task.ui.login.model.LoginResponse
import com.d2k.task.util.BaseDataSource
import com.d2k.task.util.Resources
import com.d2k.tmb.base.CommanModel
import com.d2k.task.networking.IApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private var apiService: IApiService) : BaseDataSource() {
    //    suspend fun login(loginRequest: LoginRequest)=apiService.login(loginRequest)
    suspend fun login(loginRequest: LoginRequest): Flow<Resources<LoginResponse>> {
        return flow {
            val result = safeApiCall { apiService.
            login(loginRequest) }
            emit(result)
        }.flowOn(Dispatchers.IO)

    }
}