package itemEditorFlow.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import itemEditorFlow.components.ItemEditorFlowComponent.Child
import itemEditorFlow.components.ItemEditorFlowComponent.Child.PhotoTakerChild
import itemEditorFlow.components.ItemEditorFlowComponent.Config
import itemManager.components.RealItemManagerComponent
import logic.ItemManagerPreData
import photoTaker.components.PhotoTakerComponent
import photoTaker.components.RealPhotoTakerComponent


class RealItemEditorFlowComponent(
    private val componentContext: ComponentContext,
    private val exitFromFlow: () -> Unit,
    private val itemManagerPreData: ItemManagerPreData,
    private val fetchShareCareItems: () -> Unit
) : ItemEditorFlowComponent, ComponentContext by componentContext {

    override val nav = StackNavigation<Config>()
    private val _stack =
        childStack(
            source = nav,
            serializer = Config.serializer(),
            initialConfiguration = Config.ItemManager,
            childFactory = ::child,
            handleBackButton = true
        )

    override val stack: Value<ChildStack<Config, Child>>
        get() = _stack


    // I know i do shit =(
    lateinit var photoTakerComponent: PhotoTakerComponent

    private fun child(config: Config, childContext: ComponentContext): Child {
        if (!::photoTakerComponent.isInitialized) {
            photoTakerComponent =
                RealPhotoTakerComponent(componentContext.childContext("photoTakerComponent")) {
                    popOnce(PhotoTakerChild::class)
                }
        }
        return when (config) {
            Config.PhotoTaker -> PhotoTakerChild(
                photoTakerComponent = photoTakerComponent
            )

            Config.ItemManager -> Child.ItemManagerChild(
                itemManagerComponent = RealItemManagerComponent(
                    childContext, photoTakerComponent = photoTakerComponent,
                    closeFlow = exitFromFlow,
                    openPhotoTakerComponent = { nav.bringToFront(Config.PhotoTaker) },
                    itemManagerPreData = itemManagerPreData,
                    fetchShareCareItems = fetchShareCareItems
                )
            )
        }
    }

}