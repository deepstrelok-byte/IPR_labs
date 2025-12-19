package transactions.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.CallMade
import androidx.compose.material.icons.automirrored.rounded.CallReceived
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import entities.Transaction
import foundation.AsyncImage
import network.getImageLink
import utils.SpacerH
import view.consts.Paddings
import view.theme.colors.CustomColors

@Composable
internal fun LazyItemScope.TransactionItem(
    transaction: Transaction
) {
    Row(
        modifier = Modifier.padding(top = Paddings.small).clip(shapes.large)
            .background(color = colorScheme.surfaceContainerHigh)
            .padding(Paddings.small).animateItem()
    ) {
        AsyncImage(
            link = getImageLink(transaction.imageLink),
            modifier = Modifier.size(75.dp)
                .clip(RoundedCornerShape(25.dp - Paddings.small)),
            contentDescription = null
        )
        SpacerH(Paddings.small)
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    transaction.title,
                    style = typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Icon(
                    if (transaction.isRecipient) Icons.AutoMirrored.Rounded.CallReceived else Icons.AutoMirrored.Rounded.CallMade,
                    null,
                    tint = if (transaction.isRecipient) LocalContentColor.current else CustomColors.green
                )
            }
            Text(
                transaction.description,
                style = typography.bodySmall,
                modifier = Modifier.alpha(.8f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
