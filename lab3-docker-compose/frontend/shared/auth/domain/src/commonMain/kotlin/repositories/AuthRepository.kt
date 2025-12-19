package repositories

import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface AuthRepository {
    fun requestCode(phone: String): Flow<NetworkState<Unit>>
    fun verifyCode(phone: String, otp: String): Flow<NetworkState<Boolean>>

    fun register(name: String, telegram: String): Flow<NetworkState<Unit>>

    fun logout() // TODO: kill token on server

    fun fetchToken(): String?
    fun fetchUserId(): String?
}