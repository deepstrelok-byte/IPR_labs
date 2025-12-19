package ktor.auth

import VerifyCodeResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import ktor.TokenProvider
import ktor.auth.models.requests.RegistrationBody
import ktor.auth.models.requests.RequestCodeBody
import ktor.auth.models.requests.VerifyCodeBody
import ktor.defaultPost
import network.NetworkState


class AuthRemoteDataSource(
    private val hc: HttpClient,
    private val tokenProvider: TokenProvider
) {
    fun requestCode(phone: String): Flow<NetworkState<Unit>> =
        hc.defaultPost(
            path = REQUEST_CODE_PATH,
            body = RequestCodeBody(phone = phone),
            tokenProvider = tokenProvider
        )


    fun verifyCode(phone: String, code: String): Flow<NetworkState<VerifyCodeResponse>> =
        hc.defaultPost(
            path = VERIFY_CODE_PATH,
            body = VerifyCodeBody(phone = phone, code = code),
            tokenProvider = tokenProvider
        )


    fun registerUser(name: String, telegram: String): Flow<NetworkState<Unit>> =
        hc.defaultPost(
            path = REGISTRATION_PATH,
            body = RegistrationBody(name = name, telegram = telegram),
            tokenProvider = tokenProvider
        )


    private companion object {
        const val PRE_PATH = "/auth"

        const val REQUEST_CODE_PATH = "$PRE_PATH/request-otp"
        const val VERIFY_CODE_PATH = "$PRE_PATH/verify-otp"
        const val REGISTRATION_PATH = "$PRE_PATH/registration"
    }
}