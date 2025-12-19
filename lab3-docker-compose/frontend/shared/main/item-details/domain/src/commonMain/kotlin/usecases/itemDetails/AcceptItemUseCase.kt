package usecases.itemDetails

import repositories.ItemDetailsRepository


class AcceptItemUseCase(
    private val repository: ItemDetailsRepository,
) {
    operator fun invoke(itemId: String) = repository.acceptItem(itemId)
}