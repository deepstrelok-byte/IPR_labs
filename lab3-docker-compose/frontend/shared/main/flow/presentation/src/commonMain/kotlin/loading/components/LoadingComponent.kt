package loading.components

import kotlinx.coroutines.flow.StateFlow
import network.NetworkState

interface LoadingComponent {
    val updateUserInfoResult: StateFlow<NetworkState<Unit>>
    val helloText: String

    fun updateUserInfo()
}