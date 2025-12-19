package entities

import entity.ItemResponse

data class ShareCareItems(
    val responses: List<ItemResponse>,
    val myPublishedItems: List<ItemResponse>
)
