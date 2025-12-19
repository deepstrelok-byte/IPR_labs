package ktor.models.response

import dto.ItemResponseDTO
import dto.RequestResponseDTO
import dto.toDomain
import entities.FindHelpBasic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FindHelpBasicResponse(
    @SerialName("ready_to_help") val readyToHelp: List<ItemResponseDTO>,
    @SerialName("my_requests") val myRequests: List<RequestResponseDTO>
) {
    fun toDomain() = FindHelpBasic(
        readyToHelp = readyToHelp.map { it.toDomain() },
        myRequests = myRequests.map { it.toDomain() },
    )
}