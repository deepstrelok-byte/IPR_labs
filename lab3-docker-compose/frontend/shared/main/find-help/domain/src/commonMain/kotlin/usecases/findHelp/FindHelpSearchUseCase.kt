package usecases.findHelp

import entity.SearchRequest
import repositories.FindHelpRepository

class FindHelpSearchUseCase(
    private val repository: FindHelpRepository
) {
    operator fun invoke(searchRequest: SearchRequest) = repository.search(searchRequest)
}