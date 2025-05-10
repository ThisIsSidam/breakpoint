package app.no.scroll.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.no.scroll.R

@Composable
fun TogglePlatformsSection() {
    var ytEnabled by remember { mutableStateOf(false) }
//    var instaEnabled by remember { mutableStateOf(false) }

    Column(modifier = Modifier) {
        Spacer(modifier = Modifier.height(16.dp))

        PlatformToggleCard(
            iconRes = R.drawable.ic_youtube,
            label = "Youtube Shorts",
            isChecked = ytEnabled,
            onCheckedChange = { ytEnabled = it }
        )

//        PlatformToggleCard(
//            iconRes = R.drawable.ic_instagram,
//            label = "Instagram Reels",
//            isChecked = instaEnabled,
//            onCheckedChange = { instaEnabled = it }
//        )
    }
}
