package common.grid.defaults

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import common.detailsInterfaces.DetailsConfig
import common.grid.ContentType
import common.requestCard.RequestCard
import entity.RequestResponse

@Suppress("FunctionName")
fun LazyGridScope.DefaultRequestsContent(
    requests: List<RequestResponse>,
    contentType: ContentType,
    sharedTransitionScope: SharedTransitionScope,
    filters: @Composable (() -> Unit)? = null,
    onCardClicked: (DetailsConfig.RequestDetailsConfig) -> Unit
) {
    DefaultGridContent(
        items = requests,
        key = { it.id },
        contentType = contentType,
        filters = filters
    ) { request, key ->
        DefaultRequestCard(
            request = request,
            sharedTransitionScope = sharedTransitionScope,
            key = key,
            onCardClicked = onCardClicked
        )
    }
}

@Composable
fun LazyGridItemScope.DefaultRequestCard(
    request: RequestResponse,
    sharedTransitionScope: SharedTransitionScope,
    key: String,
    onCardClicked: (DetailsConfig.RequestDetailsConfig) -> Unit
) {
    with(sharedTransitionScope) {
        RequestCard(
            modifier = Modifier
                .animateItem()
                .fillMaxSize(),
            id = request.id,
            text = request.text,
            location = request.location,
            organizationName = request.organizationName
        ) {
            onCardClicked(
                request.toConfig(key)
            )
        }
    }
}


fun RequestResponse.toConfig(key: String) = DetailsConfig.RequestDetailsConfig(
    id = this.id,
    text = this.text,
    category = this.category,
    location = this.location,
    deliveryTypes = this.deliveryTypes,
    organizationName = this.organizationName,
    creatorId = this.userId,
    key = key
)