package utils

import itemDetails.TelegramOpener
import org.koin.core.scope.Scope

expect fun Scope.initTelegramOpener(): TelegramOpener