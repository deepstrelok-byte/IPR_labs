package usecases.itemEditor

import entities.Item
import repositories.ItemEditorRepository

class CreateItemUseCase(
    private val repository: ItemEditorRepository,
) {
    operator fun invoke(item: Item) = repository.createItem(item)
}