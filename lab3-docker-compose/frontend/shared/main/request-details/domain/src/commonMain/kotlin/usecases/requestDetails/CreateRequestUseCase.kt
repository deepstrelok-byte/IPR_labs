package usecases.requestDetails

import entities.Request
import repositories.RequestDetailsRepository

class CreateRequestUseCase(
    private val repository: RequestDetailsRepository,
) {
    operator fun invoke(request: Request) = repository.createRequest(request)
}