package com.example.homeworkpm

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.homeworkpm.R

class SearchActivity : AppCompatActivity() {

    // Переменная для хранения текста поиска
    private var searchQuery: String = ""

    // View элементы
    private lateinit var backButton: ImageButton
    private lateinit var searchEditText: EditText
    private lateinit var clearButton: ImageButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Инициализация View
        backButton = findViewById(R.id.backButton)
        searchEditText = findViewById(R.id.searchEditText)
        clearButton = findViewById(R.id.clearSearchButton)
        recyclerView = findViewById(R.id.searchResultsRecyclerView)

        // Обработчик для кнопки "Назад"
        backButton.setOnClickListener {
            finish()
        }

        // Обработчик для кнопки очистки
        clearButton.setOnClickListener {
            searchEditText.text.clear()
        }

        // Добавляем TextWatcher для отслеживания изменений в EditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Не требуется
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Сохраняем текст в переменную
                searchQuery = s?.toString() ?: ""

                // Показываем или скрываем кнопку очистки в зависимости от наличия текста
                clearButton.visibility = if (s.isNullOrEmpty()) View.GONE else View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                // Не требуется
            }
        })

        // Восстанавливаем текст, если есть сохраненное состояние
        if (savedInstanceState != null) {
            val restoredText = savedInstanceState.getString("SEARCH_QUERY", "")
            searchEditText.setText(restoredText)
            searchQuery = restoredText
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Сохраняем текст из переменной в Bundle
        outState.putString("SEARCH_QUERY", searchQuery)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Восстанавливаем сохраненный текст (альтернативный метод)
        savedInstanceState?.let {
            val restoredText = it.getString("SEARCH_QUERY", "")
            searchEditText.setText(restoredText)
            searchQuery = restoredText
        }
    }
}