package usecases.itemDetails

import repositories.ItemDetailsRepository


class TakeItemUseCase(
    private val repository: ItemDetailsRepository,
) {
    operator fun invoke(itemId: String) = repository.takeItem(itemId)
}