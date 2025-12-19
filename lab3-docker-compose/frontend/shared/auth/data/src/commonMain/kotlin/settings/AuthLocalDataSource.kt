package settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class AuthLocalDataSource(
    private val settings: Settings
) {

    fun logout() {
        settings[TOKEN_KEY] = null
        settings[USER_ID_KEY] = null
    }

    fun saveToken(token: String) {
        settings[TOKEN_KEY] = token
    }

    fun saveUserId(userId: String) {
        settings[USER_ID_KEY] = userId
    }

    fun fetchToken(): String? {
        val token = settings[TOKEN_KEY, ""]
        return token.ifEmpty { null }
    }

    fun fetchUserId(): String? {
        val userId = settings[USER_ID_KEY, ""]
        return userId.ifEmpty { null }
    }

    companion object {
        const val TOKEN_KEY = "tokenKey"
        const val USER_ID_KEY = "userIdKey"
    }
}