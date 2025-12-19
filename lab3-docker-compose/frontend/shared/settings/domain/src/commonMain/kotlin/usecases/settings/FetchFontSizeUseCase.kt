package usecases.settings

import repositories.SettingsRepository


class FetchFontSizeUseCase(
    private val repository: SettingsRepository,
) {
    operator fun invoke() = repository.fetchFontSize()
}