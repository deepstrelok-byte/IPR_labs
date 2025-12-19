package itemDetails

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class TelegramOpener {
    actual fun open(username: String) {
        val telegramAppUrlString = "tg://resolve?domain=$username"
        val telegramWebUrlString = "https://t.me/$username"

        val telegramAppUrl = NSURL.URLWithString(telegramAppUrlString)
        val telegramWebUrl = NSURL.URLWithString(telegramWebUrlString)

        val sharedApplication = UIApplication.sharedApplication

        if (telegramAppUrl != null && sharedApplication.canOpenURL(telegramAppUrl)) {
            // Open in Telegram app
            sharedApplication.openURL(
                url = telegramAppUrl,
                options = emptyMap<Any?, Any>(),
                completionHandler = null
            )
        } else if (telegramWebUrl != null) {
            // Fallback to web version
            sharedApplication.openURL(
                url = telegramWebUrl,
                options = emptyMap<Any?, Any>(),
                completionHandler = null
            )
        }
    }
}