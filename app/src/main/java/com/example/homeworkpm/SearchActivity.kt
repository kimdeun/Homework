package com.example.homeworkpm

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var backButton: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var placeholderLayout: LinearLayout
    private lateinit var placeholderImage: ImageView
    private lateinit var placeholderTitle: TextView
    private lateinit var placeholderText: TextView
    private lateinit var retryButton: Button
    private lateinit var adapter: TrackAdapter

    private val api = RetrofitClient.instance

    private var searchText: String = ""
    private var isRestoring = false
    private var lastQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()
        setupListeners()
        setupRecyclerView()
    }

    private fun initViews() {
        searchEditText = findViewById(R.id.etSearch)
        clearButton = findViewById(R.id.ivClear)
        backButton = findViewById(R.id.btnBack)
        recyclerView = findViewById(R.id.recycler_view)
        placeholderLayout = findViewById(R.id.placeholderLayout)
        placeholderImage = findViewById(R.id.placeholderImage)
        placeholderTitle = findViewById(R.id.placeholderTitle)
        placeholderText = findViewById(R.id.placeholderText)
        retryButton = findViewById(R.id.retryButton)
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
            hideKeyboard(searchEditText)
            finish()
        }

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
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
            hidePlaceholder()
            showEmptyList()
        }

        retryButton.setOnClickListener {
            if (lastQuery.isNotEmpty()) {
                performSearch(lastQuery)
            }
        }

        setupTextWatcher()
    }

    private fun setupTextWatcher() {
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
    }

    private fun setupRecyclerView() {
        adapter = TrackAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun performSearch() {
        val query = searchEditText.text.toString()
        if (query.isNotEmpty()) {
            lastQuery = query
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        hideKeyboard(searchEditText)
        hidePlaceholder()

        api.search(query).enqueue(object : Callback<ITunesResponse> {
            override fun onResponse(call: Call<ITunesResponse>, response: Response<ITunesResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.results.isNotEmpty()) {
                        val tracks = body.results.map { Track.fromDto(it) }
                        showTracks(tracks)
                    } else {
                        showEmptyResult()
                    }
                } else {
                    showError()
                }
            }

            override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                showError()
            }
        })
    }

    private fun showTracks(tracks: List<Track>) {
        adapter = TrackAdapter(tracks)
        recyclerView.adapter = adapter
        recyclerView.visibility = View.VISIBLE
        placeholderLayout.visibility = View.GONE
    }

    private fun showEmptyResult() {
        recyclerView.visibility = View.GONE
        placeholderLayout.visibility = View.VISIBLE

        // Иконка для пустого результата
        placeholderImage.setImageResource(R.drawable.ic_nichego_ne_nachlos)
        placeholderImage.visibility = View.VISIBLE

        // Заголовок
        placeholderTitle.text = getString(R.string.nothing_found_title)
        placeholderTitle.visibility = View.VISIBLE

        // ❌ Описание не нужно - скрываем
        placeholderText.visibility = View.GONE

        // Кнопка обновить не нужна
        retryButton.visibility = View.GONE
    }

    private fun showError() {
        recyclerView.visibility = View.GONE
        placeholderLayout.visibility = View.VISIBLE

        // Иконка для ошибки связи
        placeholderImage.setImageResource(R.drawable.ic_problemi_so_sviaziu)
        placeholderImage.visibility = View.VISIBLE

        // Заголовок
        placeholderTitle.text = getString(R.string.connection_error_title)
        placeholderTitle.visibility = View.VISIBLE

        // Описание
        placeholderText.text = getString(R.string.connection_error_text)
        placeholderText.visibility = View.VISIBLE

        // Кнопка обновить видна
        retryButton.visibility = View.VISIBLE
    }

    private fun showEmptyList() {
        adapter = TrackAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.visibility = View.VISIBLE
        placeholderLayout.visibility = View.GONE
    }

    private fun hidePlaceholder() {
        placeholderLayout.visibility = View.GONE
    }

    private fun updateClearButtonVisibility() {
        clearButton.visibility = if (searchEditText.text.isNullOrEmpty()) View.GONE else View.VISIBLE
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
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

    override fun onBackPressed() {
        hideKeyboard(searchEditText)
        super.onBackPressed()
    }

    companion object {
        private const val KEY_SEARCH_TEXT = "KEY_SEARCH_TEXT"
    }
}