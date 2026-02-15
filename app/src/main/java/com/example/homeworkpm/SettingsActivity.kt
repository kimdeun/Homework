package com.example.homeworkpm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        // Кнопка "Назад"
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Кнопка "Поделиться приложением"
        findViewById<TextView>(R.id.tvShare).setOnClickListener {
            shareApp()
        }

        // Кнопка "Написать в поддержку"
        findViewById<TextView>(R.id.tvSupport).setOnClickListener {
            sendEmail()
        }

        // Кнопка "Пользовательское соглашение"
        findViewById<TextView>(R.id.tvAgreement).setOnClickListener {
            openAgreement()
        }
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message))
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.settings_share_app)))
    }

    private fun sendEmail() {
        val email = getString(R.string.support_email)
        val subject = getString(R.string.support_subject)
        val body = getString(R.string.support_body)

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        // Проверяем, есть ли почтовый клиент на устройстве
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        }
    }

    private fun openAgreement() {
        val url = getString(R.string.agreement_url)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

        // Проверяем, есть ли браузер на устройстве
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
