package myProfile.ui.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import utils.SpacerH
import view.consts.Paddings
import widgets.expressiveList.ExpressiveHorizontalListDefaults
import widgets.expressiveList.ExpressiveListItem
import widgets.expressiveList.ExpressiveVerticalListItem

@Composable
internal fun QuitButtonSection(
    onClick: () -> Unit
) {

    var isGoingToDelete by remember { mutableStateOf(false) }

    val quit = ExpressiveListItem(
        icon = Icons.AutoMirrored.Rounded.Logout,
        text = "Выйти из аккаунта",
        containerColor = colorScheme.errorContainer,
        onClick = { isGoingToDelete = true }
    )

    val cancelQuit = ExpressiveListItem(
        icon = Icons.Rounded.Close,
        text = "Отмена",
        onClick = { isGoingToDelete = false }
    )

    val confirmQuit = ExpressiveListItem(
        icon = Icons.AutoMirrored.Rounded.Logout,
        text = "Выйти",
        containerColor = colorScheme.errorContainer,
        onClick = onClick
    )

    val animationSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessLow)

    AnimatedContent(isGoingToDelete, transitionSpec = {
        fadeIn(animationSpec = animationSpec)
            .togetherWith(fadeOut(animationSpec = animationSpec))
    }) { isGoing ->
        if (isGoing) {
            Row {
                ExpressiveVerticalListItem(confirmQuit, modifier = Modifier.weight(1f), shape = ExpressiveHorizontalListDefaults.startShape)
                SpacerH(Paddings.ultraSmall)
                ExpressiveVerticalListItem(cancelQuit, modifier = Modifier.weight(1f), shape = ExpressiveHorizontalListDefaults.endShape)
            }
        } else {
            ExpressiveVerticalListItem(quit, modifier = Modifier.fillMaxWidth())
        }
    }
}