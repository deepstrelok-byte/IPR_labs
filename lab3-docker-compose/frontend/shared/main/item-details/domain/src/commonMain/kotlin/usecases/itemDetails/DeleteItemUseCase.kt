package usecases.itemDetails

import repositories.ItemDetailsRepository


class DeleteItemUseCase(
    private val repository: ItemDetailsRepository,
) {
    operator fun invoke(itemId: String) = repository.deleteItem(itemId)
}