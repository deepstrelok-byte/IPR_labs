package usecases.sharecare

import entity.SearchRequest
import repositories.ShareCareRepository

class ShareCareSearchUseCase(
    private val repository: ShareCareRepository
) {
    operator fun invoke(searchRequest: SearchRequest) = repository.search(searchRequest)
}