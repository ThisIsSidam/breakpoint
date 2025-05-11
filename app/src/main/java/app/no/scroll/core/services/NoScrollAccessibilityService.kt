package app.no.scroll.core.services

import android.accessibilityservice.AccessibilityService
import android.content.SharedPreferences
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class NoScrollAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val type = event.eventType

        if (type != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED &&
            type != AccessibilityEvent.TYPE_VIEW_SELECTED &&
            type != AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            return
        }

        val preferences : SharedPreferences = getSharedPreferences("app.no.scroll", MODE_PRIVATE)
        val ytAllowed : Boolean = preferences.getBoolean("yt_allowed", true)
        val instaAllowed : Boolean = preferences.getBoolean("insta_allowed", true)
        val packageName = event.packageName?.toString()

        android.util.Log.d("NoScrollService", "Package name: $packageName")
        if (packageName == "com.google.android.youtube" && ytAllowed) {
            blockShorts()
        } else if (packageName == "com.instagram.android" && instaAllowed) {
            blockReels()
        }
    }

    private fun tapChildOrParent(node: AccessibilityNodeInfo) {
        android.util.Log.d("NoScrollService", "Checking node: $node")
        if (node.isClickable) {
            android.util.Log.d("NoScrollService", "Node is clickable, performing click")
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else {
            val parent = node.parent
            if (parent != null) {
                android.util.Log.d("NoScrollService", "Node not clickable, checking parent: $parent")
                tapChildOrParent(parent)
            } else {
                android.util.Log.w("NoScrollService", "No clickable parent found")
            }
        }
    }

    private fun blockShorts() {
        android.util.Log.d("NoScrollService", "Blocking Shorts")
        val rootNode = rootInActiveWindow
        if (rootNode == null) {
            android.util.Log.w("NoScrollService", "Root node is null")
        } else {
            val node = rootNode.findAccessibilityNodeInfosByViewId("com.google.android.youtube:id/reel_right_dyn_bar")

            android.util.Log.d("NoScrollService", "Node: $node")
            if (node != null && node.isNotEmpty()) {
                val homeButton = rootNode.findAccessibilityNodeInfosByText("Home")
                if (homeButton.isNotEmpty()) {
                    tapChildOrParent(homeButton[0])
                } else {
                    android.util.Log.w("NoScrollService", "Home button not found")
                }
            }
        }
    }

    private fun blockReels() {
        android.util.Log.d("NoScrollService", "Blocking Reels")
        val rootNode = rootInActiveWindow
        if (rootNode == null) {
            android.util.Log.w("NoScrollService", "Root node is null")
        } else {
            val layoutNode = rootNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/clips_viewer_video_layout")
            val infoNode = rootNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/clips_media_info_component")
            val overlayNode = rootNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/clips_item_overlay_component")
            android.util.Log.d("NoScrollService", "Node: $layoutNode")
            if (
                layoutNode != null && layoutNode.isNotEmpty() &&
                infoNode != null && infoNode.isNotEmpty() &&
                overlayNode != null && overlayNode.isNotEmpty()
            ) {
                android.util.Log.d("NoScrollService", "Node found, performing click, $layoutNode, $infoNode, $overlayNode")
                val homeButton = rootNode.findAccessibilityNodeInfosByViewId("com.instagram.android:id/feed_tab")
                if (homeButton != null && homeButton.isNotEmpty()) {
                    tapChildOrParent(homeButton[0])
                } else {
                    android.util.Log.w("NoScrollService", "Home button not found")
                }
            } else {
                android.util.Log.w("NoScrollService", "Node not found")
            }
        }
    }

    override fun onInterrupt() {
        // Handle service interruption (e.g. when disabled)
    }
}