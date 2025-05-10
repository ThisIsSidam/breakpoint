package app.no.scroll.ui.home

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import app.no.scroll.core.services.NoScrollAccessibilityService
import app.no.scroll.ui.components.GithubIcon
import app.no.scroll.ui.components.HomeHeadline
import app.no.scroll.ui.components.ServiceButton
import app.no.scroll.ui.components.TogglePlatformsSection

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    var isServiceEnabled by remember { mutableStateOf(isAccessibilityServiceEnabled(context)) }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isServiceEnabled = isAccessibilityServiceEnabled(context)
            }
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(2f))

            HomeHeadline()

            Spacer(modifier = Modifier.height(32.dp))

            ServiceButton(context, isServiceEnabled = isServiceEnabled)

            Spacer(modifier = Modifier.weight(1f))

            if (isServiceEnabled) {
                TogglePlatformsSection()
            }

            Spacer(modifier = Modifier.weight(2f))

        }

        GithubIcon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .absolutePadding(right = 24.dp, top = 48.dp)
        )
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
