package com.example.homeworkpm

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private val prefs by lazy {
        getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        // Кнопка "Назад"
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // ---- Switch theme ----
        val switchTheme = findViewById<SwitchMaterial>(R.id.switchTheme)
        val isDarkTheme = prefs.getBoolean(KEY_DARK_THEME, false)
        switchTheme.isChecked = isDarkTheme

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        switchTheme.setOnCheckedChangeListener { _, checked ->
            prefs.edit().putBoolean(KEY_DARK_THEME, checked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (checked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // ---- Share app with course link ----
        findViewById<View>(R.id.shareContainer).setOnClickListener {
            val shareText = getString(R.string.share_app_text) + " " +
                    getString(R.string.android_course_url)

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    getString(R.string.share_chooser_title)
                )
            )
        }

        // ---- Support (Email) ----
        findViewById<View>(R.id.supportContainer).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_email)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_subject))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.support_body))
            }
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, getString(R.string.no_email_client), Toast.LENGTH_SHORT).show()
            }
        }

        // ---- Agreement (Browser) ----
        findViewById<View>(R.id.agreementContainer).setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.agreement_url))
            )
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    companion object {
        private const val PREFS_NAME = "playlist_maker_prefs"
        private const val KEY_DARK_THEME = "dark_theme"
    }
}