package usecases

import repositories.ItemDetailsRepository
import usecases.itemDetails.AcceptItemUseCase
import usecases.itemDetails.DeleteItemUseCase
import usecases.itemDetails.DenyItemUseCase
import usecases.itemDetails.FetchItemQuickInfoUseCase
import usecases.itemDetails.TakeItemUseCase

class ItemDetailsUseCases(
    repository: ItemDetailsRepository
) {
    val takeItem = TakeItemUseCase(repository)
    val acceptItem = AcceptItemUseCase(repository)
    val denyItem = DenyItemUseCase(repository)
    val deleteItem = DeleteItemUseCase(repository)

    val fetchItemQuickInfo: FetchItemQuickInfoUseCase = FetchItemQuickInfoUseCase(repository)
}