package utils

import itemDetails.TelegramOpener

actual fun org.koin.core.scope.Scope.initTelegramOpener(): itemDetails.TelegramOpener =
    TelegramOpener(get())