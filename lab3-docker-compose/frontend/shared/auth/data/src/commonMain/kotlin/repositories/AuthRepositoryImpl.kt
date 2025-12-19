package repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ktor.auth.AuthRemoteDataSource
import network.NetworkState
import settings.AuthLocalDataSource

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
) : AuthRepository {
    override fun requestCode(phone: String) = remoteDataSource.requestCode(phone)

    override fun verifyCode(
        phone: String,
        otp: String
    ): Flow<NetworkState<Boolean>> = flow {
        remoteDataSource.verifyCode(phone = phone, code = otp).collect { verifyCodeResponse ->
            emit(verifyCodeResponse.defaultWhen { response ->
                localDataSource.saveToken(response.data.token)
                localDataSource.saveUserId(response.data.userId)
                delay(1000) //=(((
                (NetworkState.Success(response.data.name == null))
            })
        }
    }.flowOn(Dispatchers.IO)

    override fun register(
        name: String,
        telegram: String
    ): Flow<NetworkState<Unit>> =
        remoteDataSource.registerUser(name = name, telegram = telegram.removePrefix("@"))

    override fun logout() = localDataSource.logout()

    override fun fetchToken(): String? = localDataSource.fetchToken()
    override fun fetchUserId(): String? = localDataSource.fetchUserId()
}