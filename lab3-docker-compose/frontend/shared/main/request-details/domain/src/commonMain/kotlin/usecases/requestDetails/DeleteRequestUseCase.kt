package usecases.requestDetails

import repositories.RequestDetailsRepository

class DeleteRequestUseCase(
    private val repository: RequestDetailsRepository,
) {
    operator fun invoke(requestId: String) = repository.deleteRequest(requestId=requestId)
}