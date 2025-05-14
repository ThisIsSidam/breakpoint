package de.scroll.app.core.extensions

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.show()
}