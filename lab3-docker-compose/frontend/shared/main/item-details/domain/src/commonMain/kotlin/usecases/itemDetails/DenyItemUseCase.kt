package usecases.itemDetails

import repositories.ItemDetailsRepository


class DenyItemUseCase(
    private val repository: ItemDetailsRepository,
) {
    operator fun invoke(itemId: String) = repository.denyItem(itemId)
}