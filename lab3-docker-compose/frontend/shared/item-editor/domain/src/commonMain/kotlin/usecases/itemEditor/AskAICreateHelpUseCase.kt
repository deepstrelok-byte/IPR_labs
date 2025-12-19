package usecases.itemEditor

import repositories.ItemEditorRepository

class AskAICreateHelpUseCase(
    private val repository: ItemEditorRepository,
) {
    operator fun invoke(images: List<ByteArray>) = repository.askAICreateHelp(images)
}