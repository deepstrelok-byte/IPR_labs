package ktor.user

import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import ktor.TokenProvider
import ktor.auth.models.responses.UserFullInfoResponse
import ktor.defaultGet
import ktor.defaultPost
import network.NetworkState

class UserRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {
    fun getCurrentUserInfo(): Flow<NetworkState<UserFullInfoResponse>> =
        hc.defaultGet(path = GET_CURRENT_USER_INFO_PATH, tokenProvider = tokenProvider)

    fun changeVerificationInfo(isVerified: Boolean, organizationName: String?): Flow<NetworkState<Unit>> =
        hc.defaultPost(path = CHANGE_VERIFICATION_PATH, tokenProvider = tokenProvider) {
            parameter("verified", isVerified)
            parameter("organization_name", organizationName)
        }

    private companion object {
        const val PRE_PATH = "/user"
        const val GET_CURRENT_USER_INFO_PATH = "$PRE_PATH/me"
        const val CHANGE_VERIFICATION_PATH = "$PRE_PATH/verification"
    }
}