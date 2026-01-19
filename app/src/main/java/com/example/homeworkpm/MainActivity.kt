
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


import android.os.Bundle
import android.view.View //
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkpm.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        val btnSearch = findViewById<MaterialButton>(R.id.btnSearch)
        val btnMedia = findViewById<MaterialButton>(R.id.btnMedia)
        val btnSettings = findViewById<MaterialButton>(R.id.btnSettings)

        btnSearch.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }


        btnMedia.setOnClickListener {
            startActivity(Intent(this, MediaLibraryActivity::class.java))
        }


        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))

        setContentView(R.layout.activity_main) // l

        //
        val searchButton = findViewById<Button>(R.id.search_button)
        val libraryButton = findViewById<Button>(R.id.library_button)
        val settingsButton = findViewById<Button>(R.id.settings_button)

        //
        val searchClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(
                    this@MainActivity,
                    "Нажата кнопка Поиск",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //
        val libraryClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(
                    this@MainActivity,
                    "Нажата кнопка Библиотека",
                    Toast.LENGTH_SHORT
                ).show()
            }
 main
        }

        //
        val settingsClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(
                    this@MainActivity,
                    "Нажата кнопка Настройки",
                    Toast.LENGTH_LONG // Пример использования LENGTH_LONG
                ).show()
            }
        }

        // Привязываем слушатели
        searchButton.setOnClickListener(searchClickListener)
        libraryButton.setOnClickListener(libraryClickListener)
        settingsButton.setOnClickListener(settingsClickListener)
    }
}