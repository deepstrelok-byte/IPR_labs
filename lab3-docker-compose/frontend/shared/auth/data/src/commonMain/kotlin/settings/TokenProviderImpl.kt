package settings

import ktor.TokenProvider

class TokenProviderImpl(val authLocalDataSource: AuthLocalDataSource) : TokenProvider {
    override fun getToken(): String? {
        return authLocalDataSource.fetchToken()
    }
}