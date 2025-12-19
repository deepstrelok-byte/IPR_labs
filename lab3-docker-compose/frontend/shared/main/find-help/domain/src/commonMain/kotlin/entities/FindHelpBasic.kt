package entities

import entity.ItemResponse
import entity.RequestResponse

data class FindHelpBasic(
    val readyToHelp: List<ItemResponse>,
    val myRequests: List<RequestResponse>
)
