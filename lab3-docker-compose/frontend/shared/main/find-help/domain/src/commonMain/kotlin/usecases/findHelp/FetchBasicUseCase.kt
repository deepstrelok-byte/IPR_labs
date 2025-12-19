package usecases.findHelp

import repositories.FindHelpRepository

class FetchBasicUseCase(
    private val repository: FindHelpRepository
) {
    operator fun invoke() = repository.fetchBasic()
}