package fi.tuni.userapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.bottomnavigation.BottomNavigationView
import fi.tuni.userapi.adapters.Adapter
import fi.tuni.userapi.models.User
import fi.tuni.userapi.models.UsersJsonObject
import fi.tuni.userapi.controllers.getRequest

import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_view)

        // search for users
        val search : EditText = findViewById(R.id.Search)
        generateUsersList()
        search.addTextChangedListener{
            if (search.text.isNullOrEmpty()) {
                generateUsersList()
            } else {
                generateUsersList("https://dummyjson.com/users/search?q=${search.text}")
            }
        }


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



    private fun generateUsersList(url: String = "https://dummyjson.com/users") {
        val usersList = mutableListOf<User>()
        thread {
            // connection to dummyjson
            val result = getRequest(url)

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
