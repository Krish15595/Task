package com.d2k.task.ui.dashboard.userlistview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d2k.task.util.Resources
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

public class UserListViewModel @Inject constructor(private val userListViewRepository: UserListViewRepository) : ViewModel() {
    private val _userDetailsFlow = Channel<Resources<UserListResponse>>(BUFFERED)
    val userDetailsFlow =  _userDetailsFlow.receiveAsFlow()
    fun userDetailsFlow() {
        viewModelScope.launch {
            _userDetailsFlow.send(Resources.loading(null))
            userListViewRepository.userList().catch {
                _userDetailsFlow.send(Resources.error(null, message = it.message ?: "Error occured"))
            }.collect {
                _userDetailsFlow.send(it)
            }
        }
    }
}