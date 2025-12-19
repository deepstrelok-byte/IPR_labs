package common.grid

sealed class ContentType(val key: String) {
    // FindHelp
    data object ReadyToHelp : ContentType("ready_to_help_type")
    data object MyRequests : ContentType("my_requests_type")
    data object Catalog : ContentType("catalog_type")

    // ShareCare
    data object Responses : ContentType("responses_type")
    data object MyItems : ContentType("my_items_type")
    data object PeopleSearch : ContentType("people_search_type")
}

fun ContentType.parseName(): String {
    return when (this) {
        ContentType.ReadyToHelp -> "Готовы помочь"
        ContentType.Catalog -> "Каталог"
        ContentType.MyItems -> "Мои вещи"
        ContentType.Responses -> "Отклики"
        ContentType.PeopleSearch -> "Люди ищут"
        ContentType.MyRequests -> "Мои заявки"
    }
}