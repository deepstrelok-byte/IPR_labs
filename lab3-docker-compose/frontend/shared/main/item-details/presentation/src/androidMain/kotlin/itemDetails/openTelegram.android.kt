package itemDetails

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri


actual class TelegramOpener(private val context: Context) {
    actual fun open(username: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = "tg://resolve?domain=$username".toUri()
            setPackage("org.telegram.messenger")
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            val webIntent = Intent(Intent.ACTION_VIEW, "https://t.me/$username".toUri()).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(webIntent)
        }
    }
}
