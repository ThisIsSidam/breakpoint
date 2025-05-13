package breakpoint.app.core.utils

import android.content.Context
import android.content.Intent
import breakpoint.app.core.extensions.toast
import androidx.core.net.toUri

class AppUtils {
    companion object {
        fun openUrl(context: Context, url: String) {
            var fixedUrl = url
            // Ensure the URL starts with http:// or https://
            if (!fixedUrl.startsWith("http://") && !fixedUrl.startsWith("https://")) {
                fixedUrl = "http://$fixedUrl"
            }

            try {
                val intent = Intent(Intent.ACTION_VIEW, fixedUrl.toUri())
                context.startActivity(intent)
            } catch (_: Exception) {
                context.toast("Failed to open URL: $url")
            }


        }

    }
}