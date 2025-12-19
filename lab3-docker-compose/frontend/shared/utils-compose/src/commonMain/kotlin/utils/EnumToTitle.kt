package utils

import logic.enums.DeliveryType
import logic.enums.DeliveryType.Mail
import logic.enums.DeliveryType.OwnerDelivery
import logic.enums.DeliveryType.Pickup
import logic.enums.ItemCategory
import logic.enums.ItemCategory.Clothes
import logic.enums.ItemCategory.Electronics
import logic.enums.ItemCategory.Household
import logic.enums.ItemCategory.Other
import logic.enums.ItemCategory.Toys

val DeliveryType.title: String
    get() = when (this) {
        Pickup -> "Самовывоз"
        Mail -> "Доставка почтой"
        OwnerDelivery -> "Человек доставит сам"
    }

val ItemCategory.title: String
    get() = when (this) {
        Clothes -> "Одежда"
        Toys -> "Игрушки"
        Household -> "Быт"
        Electronics -> "Электроника"
        Other -> "Другое"
    }