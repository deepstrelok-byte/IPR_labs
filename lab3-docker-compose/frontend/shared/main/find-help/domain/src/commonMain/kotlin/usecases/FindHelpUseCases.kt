package usecases

import repositories.FindHelpRepository
import usecases.findHelp.FetchBasicUseCase
import usecases.findHelp.FindHelpSearchUseCase

class FindHelpUseCases(
    private val repository: FindHelpRepository
) {
    val fetchBasic = FetchBasicUseCase(repository)
    val search = FindHelpSearchUseCase(repository)
}