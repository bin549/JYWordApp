package com.android.jywordapp.activities

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.android.jywordapp.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_progress.*
import android.os.CountDownTimer
import com.android.jywordapp.WordApp
import com.android.jywordapp.model.UserEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class RegisterActivity : AppCompatActivity() {
    private lateinit var mProgressDialog: Dialog
    private var restTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val toolbar_register_activity: Toolbar = findViewById(R.id.toolbar_register_activity)
        setupActionBar(toolbar_register_activity)
        var btn_sign_up: Button = findViewById(R.id.btn_sign_up)
        val et_name: AppCompatEditText = findViewById(R.id.et_username)
        val et_email: AppCompatEditText = findViewById(R.id.et_email)
        val et_password: AppCompatEditText = findViewById(R.id.et_password)
        btn_sign_up.setOnClickListener {
            registerUser(et_name, et_email, et_password)
        }
    }

    private fun setupActionBar(toolbar_register_activity: Toolbar) {
        setSupportActionBar(toolbar_register_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser(
        et_name: AppCompatEditText,
        et_email: AppCompatEditText,
        et_password: AppCompatEditText
    ) {
        val username: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        val userDao = (application as WordApp).db.userDao()
        if (validateForm(username, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))
            GlobalScope.launch {
                delay(1000)
                userDao.insert(
                    UserEntity(
                        id = Random.nextInt(),
                        username = username,
                        email = email,
                        password = password
                    )
                )
                startActivity(Intent(this@RegisterActivity, ProfileActivity::class.java))
            }
//            hideProgressDialog()
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("请输入名字.")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("请输入邮箱.")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("请输入密码.")
                false
            }
            else -> {
                true
            }
        }
    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.tv_progress_text.text = text
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(
            ContextCompat.getColor(
                this@RegisterActivity,
                R.color.skyBlue
            )
        )
        snackBar.show()
    }
}
