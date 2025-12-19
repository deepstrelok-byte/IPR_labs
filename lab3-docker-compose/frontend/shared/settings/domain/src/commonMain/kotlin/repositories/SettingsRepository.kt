package repositories

import enums.UsuallyI


interface SettingsRepository {
    fun changeUsuallyI(usuallyI: UsuallyI)
    fun fetchUsuallyI(): UsuallyI

    fun changeFontSize(value: Float)
    fun fetchFontSize(): Float
}