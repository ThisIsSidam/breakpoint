package scroll.guard.app.ui.components

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ServiceButton(context: Context, isServiceEnabled: Boolean) {
    Button(
        onClick = {
            context.startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isServiceEnabled) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = if (isServiceEnabled) "Disable" else "Enable",
            color = if (isServiceEnabled) MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onPrimary,
        )
    }
}