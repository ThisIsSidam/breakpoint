package breakpoint.app.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import breakpoint.app.R
import breakpoint.app.core.utils.AppUtils

@Composable
fun GithubIcon(context: Context, modifier: Modifier) {
    IconButton(
        onClick = {
            AppUtils.openUrl(context, "https://github.com/ThisIsSidam/breakpoint")
        },
        modifier = modifier.size(32.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_github),
            contentDescription = "GitHub Icon",
            tint = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun HomeHeadline() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "End of your",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        )

        Text(
            text = "Doomscrolling",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}