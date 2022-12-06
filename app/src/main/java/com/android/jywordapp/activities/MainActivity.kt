package com.android.jywordapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.android.jywordapp.Constants
import com.android.jywordapp.R
import com.android.jywordapp.WordApp
import com.android.jywordapp.model.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

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
                val intent = Intent(this, WordEditor::class.java)
                GlobalScope.launch {
                    val user = userDao.fetchUser(etName.text.toString(), etPassword.text.toString())
                    if (user != null) {
                        intent.putExtra(Constants.USER_ID, user.id)
                        startActivity(intent)
                    }
                }
            }
        }
        buttonRegister.setOnClickListener {
            if (etName.text.toString().isNotEmpty() && etPassword.text.toString().isNotEmpty()) {
                GlobalScope.launch {
                    userDao.insert(
                        UserEntity(
                            id = Random.nextInt(),
                            username = etName.text.toString(),
                            password = etPassword.text.toString()
                        )
                    )
                }
                lifecycleScope.launch {
                    Toast.makeText(applicationContext, "用户注册成功", Toast.LENGTH_LONG).show()
                    etName?.text?.clear()
                    etPassword?.text?.clear()
                }
            } else {
                Toast.makeText(
                    this,
                    etName.text.toString() + " " + etPassword.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
