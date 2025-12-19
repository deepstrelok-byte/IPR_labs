package myProfile.ui

import FontSizeManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.activate
import common.ProfileHeader
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import dialogs.editProfile.components.EditProfileComponent
import dialogs.editProfile.ui.EditProfileUI
import dialogs.interfaces.DialogConfig
import dialogs.verification.components.VerificationComponent
import dialogs.verification.ui.VerificationUI
import foundation.scrollables.VerticalScrollableBox
import myProfile.components.MyProfileComponent
import myProfile.ui.sections.FontSizeSection
import myProfile.ui.sections.ListSection
import myProfile.ui.sections.QuitButtonSection
import myProfile.ui.sections.UsuallyISection
import utils.SpacerV
import view.consts.Paddings
import widgets.glass.BackGlassButton

@Composable
fun MyProfileUI(
    component: MyProfileComponent
) {

    val dialogsSlot by component.dialogsSlot.subscribeAsState()
    val dialogs = dialogsSlot.child?.instance

    val fontSize by FontSizeManager.fontSize.collectAsState()

    val profileData by component.profileData.collectAsState()
    val isHelper by component.isHelper.collectAsState()


    val windowInsets = WindowInsets.safeContent
    val safeContentPaddings = windowInsets.asPaddingValues()
    val topPadding = safeContentPaddings.calculateTopPadding()

    val hazeState = rememberHazeState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                Modifier.padding(
                    top = topPadding + Paddings.small,
                    start = Paddings.listHorizontalPadding
                )
            ) {
                BackGlassButton(hazeState = hazeState) {
                    component.goToMain()
                }
            }
        }
    ) { padding ->
        VerticalScrollableBox(
            modifier = Modifier.fillMaxSize()
                .hazeSource(hazeState),
            windowInsets = windowInsets,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = Paddings.listHorizontalPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpacerV(padding.calculateTopPadding() - topPadding)
                ProfileHeader(
                    profileData = profileData
                ) { component.dialogsNav.activate(DialogConfig.Verification) }
                SpacerV(Paddings.medium)

                UsuallyISection(
                    isHelper = isHelper
                ) { isHelper ->
                    component.changeUsuallyI(isHelper)
                }
                SpacerV(Paddings.medium)

                FontSizeSection(
                    value = fontSize,
                    onChange = component::changeFontSize
                )

                SpacerV(Paddings.medium)

                ListSection(
                    onProfileEditClick = {
                        component.dialogsNav.activate(
                            DialogConfig.EditProfile
                        )
                    }, onOperationsClick = component::openTransactions
                )

                SpacerV(Paddings.medium)

                QuitButtonSection(onClick = component::logout)

                SpacerV(Paddings.endListPadding)
            }
        }
    }
    if (dialogs != null) {
        when (dialogs) {
            is EditProfileComponent -> EditProfileUI(dialogs)
            is VerificationComponent -> VerificationUI(dialogs)
        }
    }
}