package app.no.scroll

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.no.scroll.core.services.NoScrollAccessibilityService
import app.no.scroll.ui.home.EnablePermissionScreen
import app.no.scroll.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                EnablePermissionScreen(this)
            }
        }
    }


}

fun isAccessibilityServiceEnabled(context: Context): Boolean {
    val service = ComponentName(context, NoScrollAccessibilityService::class.java)
    val enabledServicesSetting = Settings.Secure.getString(
        context.contentResolver,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
    ) ?: return false

    val colonSplitter = TextUtils.SimpleStringSplitter(':')
    colonSplitter.setString(enabledServicesSetting)

    for (serviceString in colonSplitter) {
        if (ComponentName.unflattenFromString(serviceString) == service) {
            return true
        }
    }
    return false
}


