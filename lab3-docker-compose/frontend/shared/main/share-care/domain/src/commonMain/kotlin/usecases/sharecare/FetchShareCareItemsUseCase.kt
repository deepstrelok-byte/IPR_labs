package usecases.sharecare

import repositories.ShareCareRepository

class FetchShareCareItemsUseCase(
    private val repository: ShareCareRepository
) {
    operator fun invoke() = repository.fetchItems()
}