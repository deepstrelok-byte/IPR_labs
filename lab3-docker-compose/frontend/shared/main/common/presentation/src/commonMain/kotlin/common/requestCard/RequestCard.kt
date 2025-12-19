package common.requestCard

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import architecture.launchIO
import utils.SpacerV
import view.consts.Paddings
import widgets.SimpleChip

@Composable
fun SharedTransitionScope.RequestCard(
    modifier: Modifier = Modifier,
    id: String,
    text: String,
    location: String,
    organizationName: String?,
    onClick: () -> Unit
) {

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()


    Card(
        modifier = modifier
            .clip(RequestCardDefaults.cardShape)
            .bringIntoViewRequester(bringIntoViewRequester)
            .clickable {
                coroutineScope.launchIO {
                    bringIntoViewRequester.bringIntoView()
                }
                onClick()
            },
        shape = RequestCardDefaults.cardShape
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = Paddings.small).padding(horizontal = Paddings.semiMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text,
                maxLines = 2,
                minLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            SpacerV(Paddings.semiSmall)

            Box(contentAlignment = Alignment.Center) {
                if (organizationName != null) {
                    SimpleChip(organizationName)
                } else {
                    Text(
                        location,
                        maxLines = 1,
                        style = typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.alpha(.7f),
                    )
                }
                SimpleChip("Для сохранения одинакового размера", modifier = Modifier.alpha(0f))
            }
        }
    }
}