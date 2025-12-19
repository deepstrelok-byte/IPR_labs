package common

import androidx.compose.runtime.Composable
import logic.QuickProfileData
import myProfile.ui.sections.NameSection
import myProfile.ui.sections.VerificationSection
import utils.SpacerV
import view.consts.Paddings
import widgets.Avatar

@Composable
fun ProfileHeader(
    profileData: QuickProfileData,
    onVerificationClick: () -> Unit
) {
    Avatar()

    SpacerV(Paddings.medium)
    NameSection(profileData.name)
    SpacerV(Paddings.medium)

    VerificationSection(
        isVerified = profileData.isVerified,
        organizationName = profileData.organizationName
    ) { onVerificationClick() }
}