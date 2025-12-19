package usecases.requestDetails

import repositories.RequestDetailsRepository


class FetchRequestQuickInfoUseCase(
    private val repository: RequestDetailsRepository,
) {
    operator fun invoke(requestId: String) = repository.fetchRequestQuickInfo(requestId)
}