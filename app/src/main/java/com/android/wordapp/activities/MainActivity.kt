package com.android.wordapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.wordapp.Constants
import com.android.wordapp.R
import com.android.wordapp.WordApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userDao = (application as WordApp).db.userDao()
        val buttonLogin: Button = findViewById(R.id.btn_login)
        val buttonRegister: Button = findViewById(R.id.btn_register)
        val etName: EditText = findViewById(R.id.et_username)
        val etPassword: EditText = findViewById(R.id.et_password)
        buttonLogin.setOnClickListener {
            if (etName.text.toString().isEmpty() || etPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "用户名或者密码不能为空.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                GlobalScope.launch {
                    val user = userDao.fetchUser(etName.text.toString(), etPassword.text.toString())
                    if (user != null) {
                        intent.putExtra(Constants.USER_ID, user.id)
                        intent.putExtra(Constants.USER_Name, user.username)
                        startActivity(intent)
                    }
                }
            }
        }
        buttonRegister.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }
    }
}
