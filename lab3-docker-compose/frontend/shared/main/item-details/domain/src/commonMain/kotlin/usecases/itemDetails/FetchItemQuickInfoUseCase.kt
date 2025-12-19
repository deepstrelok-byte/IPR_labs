package usecases.itemDetails

import repositories.ItemDetailsRepository


class FetchItemQuickInfoUseCase(
    private val repository: ItemDetailsRepository,
) {
    operator fun invoke(itemId: String) = repository.fetchItemQuickInfo(itemId)
}