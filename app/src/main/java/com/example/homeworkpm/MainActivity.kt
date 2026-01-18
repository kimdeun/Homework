
import android.os.Bundle
import android.view.View //
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.homeworkpm.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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