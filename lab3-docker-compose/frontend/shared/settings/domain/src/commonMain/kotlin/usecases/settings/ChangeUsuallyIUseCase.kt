package usecases.settings

import enums.UsuallyI
import repositories.SettingsRepository


class ChangeUsuallyIUseCase(
    private val repository: SettingsRepository,
) {
    operator fun invoke(usuallyI: UsuallyI) = repository.changeUsuallyI(usuallyI)
}