package usecases.settings

import repositories.SettingsRepository


class ChangeFontSizeUseCase(
    private val repository: SettingsRepository,
) {
    operator fun invoke(value: Float) = repository.changeFontSize(value)
}