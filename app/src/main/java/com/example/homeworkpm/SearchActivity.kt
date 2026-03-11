package com.example.homeworkpm

import android.content.Context
import com.example.homeworkpm.TrackAdapter
import com.example.homeworkpm.Track
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TrackAdapter

    private var searchText: String = ""
    private var isRestoring = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // Инициализируем все View
        searchEditText = findViewById(R.id.etSearch)
        clearButton = findViewById(R.id.ivClear)
        backButton = findViewById(R.id.btnBack)
        recyclerView = findViewById(R.id.recycler_view)  // !!! Инициализируем RecyclerView

        // !!! Настраиваем RecyclerView со списком треков
        setupRecyclerView()

        // Настройка кнопки назад
        backButton.setOnClickListener {
            hideKeyboard(searchEditText)
            finish()
        }

        searchEditText.hint = getString(R.string.search_hint)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isRestoring) return

                searchText = s?.toString().orEmpty()
                updateClearButtonVisibility()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        searchEditText.addTextChangedListener(textWatcher)

        // Обработка нажатия кнопки "Поиск" на клавиатуре
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        // Обработка кнопки очистки
        clearButton.setOnClickListener {
            searchEditText.text.clear()
            searchEditText.requestFocus()
            showKeyboard(searchEditText)
            updateClearButtonVisibility()
            searchText = ""
        }
    }

    // !!! НОВЫЙ МЕТОД: настройка RecyclerView с мок-данными
    private fun setupRecyclerView() {
        // Получаем мок-данные
        val mockTracks = getMockTracks()

        // Создаем адаптер
        adapter = TrackAdapter(mockTracks)

        // Настраиваем RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    // !!! НОВЫЙ МЕТОД: создание мок-данных для списка треков
    private fun getMockTracks(): List<Track> {
        return listOf(
            Track(
                "Smells Like Teen Spirit",
                "Nirvana",
                "5:01",
                "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
            ),
            Track(
                "Billie Jean",
                "Michael Jackson",
                "4:35",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
            ),
            Track(
                "Stayin' Alive",
                "Bee Gees",
                "4:10",
                "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
            ),
            Track(
                "Whole Lotta Love",
                "Led Zeppelin",
                "5:33",
                "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
            ),
            Track(
                "Sweet Child O'Mine",
                "Guns N' Roses",
                "5:03",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
            )
        )
    }

    private fun updateClearButtonVisibility() {
        clearButton.visibility = if (searchEditText.text.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun performSearch() {
        val query = searchEditText.text.toString()
        if (query.isNotEmpty()) {
            Toast.makeText(this, "Поиск: $query", Toast.LENGTH_SHORT).show()
            hideKeyboard(searchEditText)

            // !!! Здесь в будущем будет поиск через API
            // А пока просто показываем, что поиск работает
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SEARCH_TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        isRestoring = true
        val value = savedInstanceState.getString(KEY_SEARCH_TEXT, "")
        searchEditText.setText(value)
        if (value.isNotEmpty()) {
            searchEditText.setSelection(value.length)
        }
        updateClearButtonVisibility()
        searchText = value
        isRestoring = false
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onBackPressed() {
        hideKeyboard(searchEditText)
        super.onBackPressed()
    }

    companion object {
        private const val KEY_SEARCH_TEXT = "KEY_SEARCH_TEXT"
    }
}