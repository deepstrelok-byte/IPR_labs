package usecases

import repositories.RequestDetailsRepository
import usecases.requestDetails.CreateRequestUseCase
import usecases.requestDetails.DeleteRequestUseCase
import usecases.requestDetails.EditRequestUseCase
import usecases.requestDetails.FetchRequestQuickInfoUseCase

class RequestDetailsUseCases(
    repository: RequestDetailsRepository
) {
    val createRequest = CreateRequestUseCase(repository)
    val editRequest = EditRequestUseCase(repository)
    val deleteRequest = DeleteRequestUseCase(repository)
    val fetchRequestQuickInfo = FetchRequestQuickInfoUseCase(repository)
}