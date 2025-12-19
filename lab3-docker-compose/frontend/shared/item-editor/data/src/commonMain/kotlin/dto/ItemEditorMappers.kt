package dto

import entities.AICreateHelp
import entities.Item

fun Item.toDTO() = ItemDTO(
    title = this.title,
    description = this.description,
    location = this.location,
    category = this.category.name,
    deliveryTypes = this.deliveryTypes.map { it.name },
    images = this.images,
    requestId = this.requestId
)

fun AICreateHelpDTO.toDomain() = AICreateHelp(
    title = name,
    description = description,
    category = category
)