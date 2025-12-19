package usecases

import repositories.ShareCareRepository
import usecases.sharecare.FetchShareCareItemsUseCase
import usecases.sharecare.ShareCareSearchUseCase

class ShareCareUseCases(
    repository: ShareCareRepository
) {
    val fetchItems = FetchShareCareItemsUseCase(repository)
    val search = ShareCareSearchUseCase(repository)
}