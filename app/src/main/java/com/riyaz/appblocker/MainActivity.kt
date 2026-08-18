package com.riyaz.appblocker

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getBooleanExtra("blocked_screen", false)) {
            showBlocked()
        } else {
            showLogin()
        }
    }

    private fun createLayout(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 48, 32, 32)
            setBackgroundColor(Color.rgb(11, 11, 16))
        }
    }

    private fun makeTitle(text: String, size: Float): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(Color.WHITE)
            setPadding(0, 12, 0, 12)
        }
    }

    private fun makeButton(
        text: String,
        action: () -> Unit
    ): Button {
        return Button(this).apply {
            this.text = text
            setOnClickListener {
                action()
            }
        }
    }

    private fun showLogin() {

        val layout = createLayout()

        val title = makeTitle("APP BLOCKER", 30f)
        title.gravity = Gravity.CENTER
        layout.addView(title)

        layout.addView(
            makeTitle("Protect your focus", 16f)
        )

        val username = EditText(this).apply {
            hint = "User ID"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
        }

        val password = EditText(this).apply {
            hint = "Password"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
        }

        layout.addView(username)
        layout.addView(password)

        layout.addView(
            makeButton("LOGIN") {
                showHome()
            }
        )

        setContentView(layout)
    }

    private fun showHome() {

        val layout = createLayout()

        layout.addView(
            makeTitle("APP BLOCKER", 28f)
        )

        status = makeTitle(
            "🟢 Protection Ready",
            18f
        )

        layout.addView(status)

        layout.addView(
            makeButton("🚫 BLOCK AN APP") {
                chooseDuration()
            }
        )

        layout.addView(
            makeButton("🔐 ENABLE BLOCKING") {
                startActivity(
                    Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                )
            }
        )

        layout.addView(
            makeButton("📊 STATISTICS") {
                Toast.makeText(
                    this,
                    "Statistics will appear here.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        layout.addView(
            makeButton("⚙️ SETTINGS") {
                Toast.makeText(
                    this,
                    "Settings coming next.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        setContentView(layout)
    }

    private fun chooseDuration() {

        val options = arrayOf(
            "15 Minutes",
            "30 Minutes",
            "1 Hour",
            "2 Hours"
        )

        AlertDialog.Builder(this)
            .setTitle("Choose Block Duration")
            .setItems(options) { _, which ->

                val minutes = when (which) {
                    0 -> 15
                    1 -> 30
                    2 -> 60
                    else -> 120
                }

                val until =
                    System.currentTimeMillis() +
                    minutes * 60_000L

                getSharedPreferences(
                    "blocker",
                    MODE_PRIVATE
                )
                    .edit()
                    .putLong("until", until)
                    .apply()

                status.text = "🔴 Protection ON"

                Toast.makeText(
                    this,
                    "Timer started: ${options[which]}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun showBlocked() {

        val layout = createLayout()

        val title = makeTitle(
            "🚫 APP BLOCKED",
            30f
        )

        title.gravity = Gravity.CENTER

        layout.addView(title)

        layout.addView(
            makeTitle(
                "This app is currently blocked.\n\nFocus mode is active.",
                18f
            )
        )

        layout.addView(
            makeButton("GO BACK") {
                finish()
            }
        )

        setContentView(layout)
    }
}
        l.addView(btn("⚙️ SETTINGS") {
            Toast.makeText(
                this,
                "Settings coming next.",
                Toast.LENGTH_SHORT
            ).show()
        })

        setContentView(l)
    }

    private fun chooseDuration() {
        val options = arrayOf(
            "15 Minutes",
            "30 Minutes",
            "1 Hour",
            "2 Hours"
        )

        AlertDialog.Builder(this)
            .setTitle("Choose Block Duration")
            .setItems(options) { _, which ->

                val minutes = when (which) {
                    0 -> 15
                    1 -> 30
                    2 -> 60
                    else -> 120
                }

                val until =
                    System.currentTimeMillis() +
                    minutes * 60_000L

                getSharedPreferences(
                    "blocker",
                    MODE_PRIVATE
                ).edit()
                    .putLong("until", until)
                    .apply()

                status.text = "🔴 Protection ON"

                Toast.makeText(
                    this,
                    "Timer started: ${options[which]}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    private fun showBlocked() {
        val l = layout()

        val t = title("🚫 APP BLOCKED", 30f)
        t.gravity = Gravity.CENTER

        l.addView(t)

        l.addView(
            title(
                "This app is currently blocked.\n\nFocus mode is active.",
                18f
            )
        )

        l.addView(btn("GO BACK") {
            finish()
        })

        setContentView(l)
    }
}
