package dialogs.interfaces

import kotlinx.serialization.Serializable

@Serializable
sealed interface DialogConfig {
    data object Verification : DialogConfig

    data object EditProfile : DialogConfig
}