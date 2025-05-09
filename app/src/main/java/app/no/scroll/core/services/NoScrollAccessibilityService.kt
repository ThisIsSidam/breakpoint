package app.no.scroll.core.services

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class NoScrollAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString()
            android.util.Log.d("NoScrollService", "Event received for package: $packageName")
            if (packageName == "com.google.android.youtube") {
                val rootNode = rootInActiveWindow
                if (rootNode == null) {
                    android.util.Log.w("NoScrollService", "Root node is null")
                } else {
                    val node = rootNode.findAccessibilityNodeInfosByViewId("com.google.android.youtube:id/reel_right_dyn_bar")
                    if (node != null) {
                        val homeButton = rootNode.findAccessibilityNodeInfosByText("Home")
                        if (homeButton.isNotEmpty()) {
                            android.util.Log.d("NoScrollService", "Home button found, performing click")
                            tapChildOrParent(homeButton[0])
                        } else {
                            android.util.Log.w("NoScrollService", "Home button not found")
                        }
                    }
                }
            } else {
                android.util.Log.d("NoScrollService", "Package $packageName is not YouTube")
            }
        }
    }

    private fun tapChildOrParent(node: AccessibilityNodeInfo) {
        if (node.isClickable) {
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else {
            val parent = node.parent
            if (parent != null) {
                tapChildOrParent(parent)
            }
        }
    }

    override fun onInterrupt() {
        // Handle service interruption (e.g. when disabled)
    }
}