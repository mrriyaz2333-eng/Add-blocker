package com.riyaz.appblocker

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
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

    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.getBooleanExtra("blocked_screen", false)) {
            showBlockedScreen()
        } else {
            showLoginScreen()
        }
    }

    private fun baseLayout(): LinearLayout {
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(32, 50, 32, 32)
            setBackgroundColor(Color.rgb(11, 11, 16))
        }
    }

    private fun textView(
        text: String,
        size: Float
    ): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(Color.WHITE)
            setPadding(0, 12, 0, 12)
        }
    }

    private fun button(
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

    private fun showLoginScreen() {

        val layout = baseLayout()

        val title = textView(
            "APP BLOCKER",
            30f
        )

        title.gravity = Gravity.CENTER
        layout.addView(title)

        layout.addView(
            textView(
                "Login to continue",
                16f
            )
        )

        val username = EditText(this).apply {
            hint = "Username"
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
            button("LOGIN") {
                showHomeScreen()
            }
        )

        setContentView(layout)
    }

    private fun showHomeScreen() {

        val layout = baseLayout()

        layout.addView(
            textView(
                "APP BLOCKER",
                28f
            )
        )

        statusText = textView(
            "🟢 Protection is OFF",
            18f
        )

        layout.addView(statusText)

        layout.addView(
            button("🚫 START BLOCK TIMER") {
                showDurationDialog()
            }
        )

        layout.addView(
            button("🔐 ACCESSIBILITY SETTINGS") {
                try {
                    startActivity(
                        Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    )
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        "Settings open nahi ho payi",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        layout.addView(
            button("📊 STATISTICS") {
                Toast.makeText(
                    this,
                    "Statistics feature coming soon",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        layout.addView(
            button("⚙️ SETTINGS") {
                Toast.makeText(
                    this,
                    "Settings feature coming soon",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        setContentView(layout)
    }

    private fun showDurationDialog() {

        val options = arrayOf(
            "15 Minutes",
            "30 Minutes",
            "1 Hour",
            "2 Hours"
        )

        AlertDialog.Builder(this)
            .setTitle("Block Duration")
            .setItems(
                options,
                object : DialogInterface.OnClickListener {

                    override fun onClick(
                        dialog: DialogInterface?,
                        which: Int
                    ) {

                        val minutes = when (which) {
                            0 -> 15
                            1 -> 30
                            2 -> 60
                            else -> 120
                        }

                        val until =
                            System.currentTimeMillis() +
                            (minutes * 60_000L)

                        getSharedPreferences(
                            "blocker",
                            MODE_PRIVATE
                        )
                            .edit()
                            .putLong(
                                "until",
                                until
                            )
                            .apply()

                        statusText.text =
                            "🔴 Protection ON\n${options[which]}"

                        Toast.makeText(
                            this@MainActivity,
                            "Timer started",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
            .show()
    }

    private fun showBlockedScreen() {

        val layout = baseLayout()

        val title = textView(
            "🚫 APP BLOCKED",
            30f
        )

        title.gravity = Gravity.CENTER

        layout.addView(title)

        layout.addView(
            textView(
                "Focus mode is active.\n\nThis app is currently blocked.",
                18f
            )
        )

        layout.addView(
            button("GO BACK") {
                finish()
            }
        )

        setContentView(layout)
    }
}
