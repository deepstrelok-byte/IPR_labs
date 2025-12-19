package usecases.requestDetails

import entities.Request
import repositories.RequestDetailsRepository

class EditRequestUseCase(
    private val repository: RequestDetailsRepository,
) {
    operator fun invoke(request: Request, requestId: String) = repository.editRequest(request, requestId=requestId)
}