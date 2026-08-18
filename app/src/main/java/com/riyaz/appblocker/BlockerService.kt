package com.riyaz.appblocker

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class BlockerService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event?.eventType !=
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
        ) return

        val packageName =
            event.packageName?.toString() ?: return

        val prefs =
            getSharedPreferences("blocker", MODE_PRIVATE)

        val until =
            prefs.getLong("until", 0L)

        val blockedApps =
            prefs.getStringSet(
                "blocked",
                emptySet()
            ) ?: emptySet()

        if (
            until > System.currentTimeMillis() &&
            blockedApps.contains(packageName) &&
            packageName != this.packageName
        ) {

            val intent =
                Intent(this, MainActivity::class.java)

            intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP
            )

            intent.putExtra(
                "blocked_screen",
                true
            )

            startActivity(intent)
        }
    }

    override fun onInterrupt() {
        // Accessibility service interrupted
    }
}
