package usecases.settings

import repositories.SettingsRepository


class FetchUsuallyIUseCase(
    private val repository: SettingsRepository,
) {
    operator fun invoke() = repository.fetchUsuallyI()
}