package myProfile.components

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.value.Value
import dialogs.interfaces.DialogComponent
import dialogs.interfaces.DialogConfig
import kotlinx.coroutines.flow.StateFlow
import logic.QuickProfileData


interface MyProfileComponent {

    val dialogsNav: SlotNavigation<DialogConfig>
    val dialogsSlot: Value<ChildSlot<*, DialogComponent>>


    val profileData: StateFlow<QuickProfileData>

    fun openTransactions()
    val isHelper: StateFlow<Boolean>

    fun logout()
    val goToMain: () -> Unit

    fun changeUsuallyI(isHelper: Boolean)

    fun changeFontSize(value: Float)
}