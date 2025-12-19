package usecases

import repositories.SettingsRepository
import usecases.settings.ChangeFontSizeUseCase
import usecases.settings.ChangeUsuallyIUseCase
import usecases.settings.FetchFontSizeUseCase
import usecases.settings.FetchUsuallyIUseCase

class SettingsUseCases(
    repository: SettingsRepository
) {
    val changeUsuallyI = ChangeUsuallyIUseCase(repository)
    val fetchUsuallyI = FetchUsuallyIUseCase(repository)

    val fetchFontSize = FetchFontSizeUseCase(repository)
    val changeFontSize = ChangeFontSizeUseCase(repository)
}