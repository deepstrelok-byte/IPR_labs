package itemDetails.ui.bottomSheet.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import utils.SpacerV
import view.consts.Paddings
import widgets.expressiveList.ExpressiveHorizontalList
import widgets.expressiveList.ExpressiveListItem

@Composable
fun HugeButtonsSection(
    buttons: List<ExpressiveListItem>
) {
    if (buttons.isNotEmpty()) {
        val animationSpec: FiniteAnimationSpec<Float> = spring(stiffness = Spring.StiffnessLow)
        AnimatedContent(
            buttons,
            transitionSpec = {
                fadeIn(animationSpec = animationSpec)
                    .togetherWith(fadeOut(animationSpec = animationSpec))
            }
        ) { animatedButtons ->
            ExpressiveHorizontalList(
                modifier = Modifier.fillMaxWidth(),
                items = animatedButtons
            )
        }
        SpacerV(Paddings.semiMedium)
    }
}




