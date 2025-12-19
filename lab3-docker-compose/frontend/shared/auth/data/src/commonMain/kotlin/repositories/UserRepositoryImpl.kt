package repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.user.UserRemoteDataSource
import network.NetworkState
import settings.UserLocalDataSource

class UserRepositoryImpl(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    override fun updateUserInfo(): Flow<NetworkState<Unit>> = flow {
        remoteDataSource.getCurrentUserInfo().collect { userInfoResponse ->
            emit(userInfoResponse.defaultWhen { response ->
                val info = response.data
                localDataSource.updateUser(
                    name = info.name,
                    telegram = info.telegram,
                    phone = info.phone,
                    isVerified = info.isVerified,
                    organizationName = info.organizationName
                )
                NetworkState.Success(Unit)
            })
        }
    }.flowOn(Dispatchers.IO)

    override fun saveName(name: String) = localDataSource.saveName(name)

    override fun fetchName(): String? = localDataSource.fetchName()
    override fun fetchIsVerified(): Boolean = localDataSource.fetchIsVerified()

    override fun fetchOrganizationName(): String? = localDataSource.fetchOrganizationName()
    override fun changeVerification(
        isVerified: Boolean,
        organizationName: String?
    ): Flow<NetworkState<Unit>> = flow {
        remoteDataSource.changeVerificationInfo(isVerified, organizationName)
            .collect { verificationResponse ->
                emit(verificationResponse.defaultWhen {
                    localDataSource.saveIsVerified(isVerified)
                    localDataSource.saveOrganizationName(organizationName)
                    NetworkState.Success(Unit)
                }
                )
            }
    }.flowOn(Dispatchers.IO)

}