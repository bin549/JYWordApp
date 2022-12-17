package com.android.jywordapp.activities

import android.app.Dialog
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

class SignUpActivity : AppCompatActivity() {
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val toolbar_sign_up_activity: Toolbar = findViewById(R.id.toolbar_sign_up_activity)
        setupActionBar(toolbar_sign_up_activity)
        var btn_sign_up: Button = findViewById(R.id.btn_sign_up)
        val et_name: AppCompatEditText = findViewById(R.id.et_username)
        val et_email: AppCompatEditText = findViewById(R.id.et_email)
        val et_password: AppCompatEditText = findViewById(R.id.et_password)
        btn_sign_up.setOnClickListener {
            registerUser(et_name, et_email, et_password)
        }
    }

    private fun setupActionBar(toolbar_sign_up_activity: Toolbar) {
        setSupportActionBar(toolbar_sign_up_activity)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        toolbar_sign_up_activity.setNavigationOnClickListener { onBackPressed() }
    }

    private fun registerUser(
        et_name: AppCompatEditText,
        et_email: AppCompatEditText,
        et_password: AppCompatEditText
    ) {
        val username: String = et_name.text.toString().trim { it <= ' ' }
        val email: String = et_email.text.toString().trim { it <= ' ' }
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(username, email, password)) {
            showProgressDialog(resources.getString(R.string.please_wait))


            hideProgressDialog()
        }
        println(username)
        println(email)
        println(password)
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
                this@SignUpActivity,
                R.color.skyBlue
            )
        )
        snackBar.show()
    }
}
/* if (etName.text.toString().isNotEmpty() && etPassword.text.toString().isNotEmpty()) {
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
} */
