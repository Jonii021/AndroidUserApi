package fi.tuni.userapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import fi.tuni.userapi.controllers.postRequest
import fi.tuni.userapi.models.User

class AddUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val firstName: EditText = findViewById(R.id.firstName)
        val lastName: EditText = findViewById(R.id.lastName)
        val phone: EditText = findViewById(R.id.phone)
        val submit: Button = findViewById(R.id.button)

        submit.setOnClickListener{
            postRequest(User(
                firstName = firstName.text.toString(),
                lastName = lastName.text.toString(),
                phone = phone.text.toString())
            )
        }



        val bottomNavigation: BottomNavigationView = findViewById(R.id.BottomNav)
        bottomNavigation.selectedItemId = R.id.AddUser
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.AddUser -> {
                    true
                }
                else -> false
            }
        }

    }
}