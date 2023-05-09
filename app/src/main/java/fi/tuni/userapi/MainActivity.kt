package fi.tuni.userapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.bottomnavigation.BottomNavigationView
import fi.tuni.userapi.adapters.Adapter
import fi.tuni.userapi.models.User
import fi.tuni.userapi.models.UsersJsonObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)

        generateUsersList("https://dummyjson.com/users")

        // code for bottom navigation to change activities
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
    }



    private fun generateUsersList(url: String): Unit {
        val usersList = mutableListOf<User>()
// connection to dummyjson
        thread {
            var result: String? = null
            val sb = StringBuffer()
            val myUrl = URL(url)
            val connection = myUrl.openConnection() as HttpURLConnection
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            // turns into optional string
            reader.use {
                var line: String? = null
                do {
                    line = it.readLine()
                    sb.append(line)
                } while (line != null)
                result = sb.toString()
            }
            reader.close()
            //String to User object
            val mp = ObjectMapper()
            val myObject: UsersJsonObject = mp.readValue(result, UsersJsonObject::class.java)
            val users: MutableList<User>? = myObject.users
            users?.forEach {
                usersList.add(it)
            }

            runOnUiThread {
                recyclerView.adapter = Adapter(usersList)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.setHasFixedSize(true)
            }
        }
    }
}
