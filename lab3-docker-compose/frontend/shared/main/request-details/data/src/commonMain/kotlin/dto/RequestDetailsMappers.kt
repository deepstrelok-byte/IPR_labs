package dto

import entities.Request

fun Request.toDTO() = RequestDTO(
    text = this.text,
    location = this.location,
    category = this.category.name,
    deliveryTypes = this.deliveryTypes.map { it.name }
)

fun Request.toDTO(id: String) = RequestWithIdDTO(
    id = id,
    text = this.text,
    location = this.location,
    category = this.category.name,
    deliveryTypes = this.deliveryTypes.map { it.name }
)