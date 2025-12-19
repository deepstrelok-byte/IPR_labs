package repositories

import kotlinx.coroutines.flow.Flow
import network.NetworkState

interface UserRepository {
    fun updateUserInfo(): Flow<NetworkState<Unit>>


    fun saveName(name: String)
    fun fetchName(): String?


    fun fetchIsVerified(): Boolean
    fun fetchOrganizationName(): String?

    fun changeVerification(isVerified: Boolean, organizationName: String?): Flow<NetworkState<Unit>>
}