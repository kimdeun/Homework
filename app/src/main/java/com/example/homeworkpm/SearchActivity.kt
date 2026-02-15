package com.example.homeworkpm

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {
    private lateinit var searchEditText: EditText
    private lateinit var clearSearchButton: ImageButton
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        searchEditText = findViewById(R.id.searchEditText)
        clearSearchButton = findViewById(R.id.clearSearchButton)
        backButton = findViewById(R.id.backButton)
    }

    private fun setupListeners() {
        // TextWatcher для поисковой строки
        val searchTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Заглушка для будущих задач
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Показываем или скрываем кнопку очистки в зависимости от наличия текста
                clearSearchButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // Заглушка для будущих задач (поиск будет здесь)
            }
        }

        searchEditText.addTextChangedListener(searchTextWatcher)

        // Кнопка очистки
        clearSearchButton.setOnClickListener {
            searchEditText.setText("")           // Очищаем текст
            searchEditText.clearFocus()          // Убираем фокус
            hideKeyboard()                         // Прячем клавиатуру
            // Кнопка очистки скроется автоматически через TextWatcher
        }

        // Кнопка назад
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_text", searchEditText.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedText = savedInstanceState.getString("search_text", "")
        searchEditText.setText(savedText)
        // Обновляем видимость кнопки очистки
        clearSearchButton.visibility = if (savedText.isEmpty()) View.GONE else View.VISIBLE
    }
}