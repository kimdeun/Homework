package com.example.homeworkpm

import android.content.Context
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

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var backButton: ImageView  //  для кнопки назад

    private var searchText: String = ""
    private var isRestoring = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.etSearch)
        clearButton = findViewById(R.id.ivClear)
        backButton = findViewById(R.id.btnBack)  // Инициализируем кнопку назад

        //  для кнопки назад
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

        // кнопка назад
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        clearButton.setOnClickListener {
            searchEditText.text.clear()
            searchEditText.requestFocus()
            showKeyboard(searchEditText)
            updateClearButtonVisibility()
            searchText = ""
        }
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