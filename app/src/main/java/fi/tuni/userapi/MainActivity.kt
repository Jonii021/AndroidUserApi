package fi.tuni.userapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.bottomnavigation.BottomNavigationView
import fi.tuni.userapi.models.User
import fi.tuni.userapi.models.UsersJsonObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var  lv : ListView
    lateinit var  adapter : ArrayAdapter<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lv = findViewById(R.id.listView)

        adapter = ArrayAdapter<User>(this, R.layout.item, R.id.myTextView, mutableListOf<User>());
        lv.adapter = adapter






        val bottomNavigation: BottomNavigationView = findViewById(R.id.BottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Home -> {
                    true
                }
                R.id.AddUser -> {
                    val intent = Intent(this, AddUser::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


    }


    override fun onResume() {
        super.onResume()


        thread {
            println(getUrl("https://dummyjson.com/users"))

            if (getUrl("https://dummyjson.com/users") != null) {
                var json = getUrl("https://dummyjson.com/users")!!

                val mp = ObjectMapper()
                val myObject: UsersJsonObject =
                    mp.readValue(json, UsersJsonObject::class.java)
                val users: MutableList<User>? = myObject.users
                users?.forEach {
                    runOnUiThread() {
                        adapter.add(it)
                    }

                    println("${it.firstName} ${it.lastName}")
                }
            }
        }
    }


    fun getUrl (url: String) : String? {
        var result : String? = null
        val sb = StringBuffer()
        val myUrl = URL(url)
        val connection= myUrl.openConnection() as HttpURLConnection
        val reader = BufferedReader(InputStreamReader(connection.inputStream))

        reader.use {
            var line: String? = null
            do {
                line = it.readLine()
                sb.append(line)
            } while(line != null)
            result = sb.toString()
        }
        reader.close()
        return result
    }
}
