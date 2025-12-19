package settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class UserLocalDataSource(
    private val settings: Settings
) {

    fun updateUser(
        name: String,
        telegram: String,
        phone: String,
        isVerified: Boolean,
        organizationName: String?
    ) {
        settings[NAME_KEY] = name
        settings[TELEGRAM_KEY] = telegram
        settings[PHONE_KEY] = phone
        settings[IS_VERIFIED_KEY] = isVerified
        settings[ORGANIZATION_KEY] = organizationName ?: ""
    }

    fun saveName(name: String) {
        settings[NAME_KEY] = name
    }

    fun fetchName(): String? {
        return settings[NAME_KEY, ""].ifEmpty { null }
    }

    fun fetchIsVerified(): Boolean {
        return settings[IS_VERIFIED_KEY, false]
    }

    fun fetchOrganizationName(): String? {
        return settings[ORGANIZATION_KEY, ""].ifEmpty { null }
    }

    fun saveIsVerified(isVerified: Boolean) {
        settings[IS_VERIFIED_KEY] = isVerified
    }

    fun saveOrganizationName(organizationName: String?) {
        settings[ORGANIZATION_KEY] = organizationName
    }

    companion object {
        const val NAME_KEY = "nameKey"
        const val TELEGRAM_KEY = "telegramKey"
        const val PHONE_KEY = "phoneKey"
        const val IS_VERIFIED_KEY = "isVerifiedKey"
        const val ORGANIZATION_KEY = "organizationKey"
    }
}