package dto

import entity.ItemResponse
import entity.RequestResponse
import entity.SearchRequest

fun ItemResponseDTO.toDomain() = ItemResponse(
    title = title,
    description = description,
    location = location,
    category = category,
    deliveryTypes = deliveryTypes,
    id = id,
    ownerId = ownerId,
    recipientId = recipientId,
    images = images,
    telegram = telegram
)

fun RequestResponseDTO.toDomain() = RequestResponse(
    text = text,
    location = location,
    category = category,
    deliveryTypes = deliveryTypes,
    id = id,
    userId = userId,
    organizationName = organizationName
)

fun SearchRequest.toDTO() = SearchRequestDTO(
    query = query,
    category = category,
    deliveryTypes = deliveryTypes,
    offset = offset,
    toLoad = toLoad
)