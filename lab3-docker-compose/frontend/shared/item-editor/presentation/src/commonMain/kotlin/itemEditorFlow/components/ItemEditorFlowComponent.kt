package itemEditorFlow.components

import architecture.DefaultStack
import itemManager.components.ItemManagerComponent
import kotlinx.serialization.Serializable
import photoTaker.components.PhotoTakerComponent

interface ItemEditorFlowComponent: DefaultStack<ItemEditorFlowComponent.Config, ItemEditorFlowComponent.Child> {

    sealed interface Child {
        data class PhotoTakerChild(val photoTakerComponent: PhotoTakerComponent) : Child
        data class ItemManagerChild(val itemManagerComponent: ItemManagerComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object PhotoTaker : Config
        @Serializable
        data object ItemManager : Config
    }
}