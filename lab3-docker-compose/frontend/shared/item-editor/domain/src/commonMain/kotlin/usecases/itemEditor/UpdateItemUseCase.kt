package usecases.itemEditor

import entities.Item
import repositories.ItemEditorRepository

class UpdateItemUseCase(
    private val repository: ItemEditorRepository,
) {
    operator fun invoke(item: Item, itemId: String) = repository.updateItem(item, itemId)
}