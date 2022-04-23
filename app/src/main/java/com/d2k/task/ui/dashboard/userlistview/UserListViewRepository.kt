package com.d2k.task.ui.dashboard.userlistview


import com.d2k.task.util.BaseDataSource
import com.d2k.task.util.Resources
import com.d2k.task.networking.IApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserListViewRepository @Inject constructor(private var apiService: IApiService) : BaseDataSource() {
    suspend fun userList(): Flow<Resources<UserListResponse>> {
        return flow {
            val result = safeApiCall { apiService.userListResponse() }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}