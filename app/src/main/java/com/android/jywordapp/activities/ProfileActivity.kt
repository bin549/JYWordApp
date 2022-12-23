package com.android.jywordapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.android.jywordapp.Constants
import com.android.jywordapp.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.android.jywordapp.WordApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val wordEditorIntent = Intent(this@ProfileActivity, WordEditor::class.java)
        wordEditorIntent.putExtra(Constants.USER_ID, intent.getIntExtra(Constants.USER_ID,0))
        var ivCountdown: ImageView = findViewById(R.id.iv_countdown)
        var vocabularyCard: CardView = findViewById(R.id.vocabularyCard)
        var onlineCard: CardView = findViewById(R.id.onlineCard)
        var wordCard: CardView = findViewById(R.id.wordCard)
        var quizCard: CardView = findViewById(R.id.quizCard)
        var sentenceCard: CardView = findViewById(R.id.sentenceCard)
        var lockCard: CardView = findViewById(R.id.lockCard)
        var btnLogout: Button = findViewById(R.id.btn_logout)
        var tvName: TextView = findViewById(R.id.tv_name)
        var tvIntro: TextView = findViewById(R.id.tv_intro)
        val userName = intent.getStringExtra(Constants.USER_Name)
        var tvWord: TextView = findViewById(R.id.tv_word)
        var tvVocabulary: TextView = findViewById(R.id.tv_vocabulary)
        var tvVocabularyQuantity: TextView = findViewById(R.id.tv_vocabulary_quantity)
        var tvOnline: TextView = findViewById(R.id.tv_online)
        var tvOnlineQuantity: TextView = findViewById(R.id.tv_online_quantity)
        var tvCountdownDescription: TextView = findViewById(R.id.tv_countdown_description)
        var tvQuiz: TextView = findViewById(R.id.tv_quiz)
        var tvCountdownWord: TextView = findViewById(R.id.tv_countdown_word)
        var tvCountdownTime: TextView = findViewById(R.id.tv_countdown_time)
        var tvSentence: TextView = findViewById(R.id.tv_sentence)
        var tvLock: TextView = findViewById(R.id.tv_lock)
        tvName.text = "欢迎你：" + userName
        tvIntro.text = "个人简介：" + "这个人很懒，啥也没写。"
        tvWord.text = "单词本"
        tvVocabulary.text = "词汇量"
        tvVocabularyQuantity.text = "12个"
        tvOnline.text = "累计在线"
        tvOnlineQuantity.text = "42天"
        tvCountdownDescription.text = "倒计时"
        tvQuiz.text = "词汇小测"
        tvCountdownWord.text = "距离四级考试"
        tvCountdownTime.text = "还48天"
        tvSentence.text = "句子迷"
        tvLock.text = "开发中"


        dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        ivCountdown.setOnClickListener {
            DatePickerDialog(
                this@ProfileActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        vocabularyCard.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("警告")
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
            val dialogItems = arrayOf("别急！别急！别急！别急！")
            alertDialog.setItems(dialogItems) { dialog, which ->
                when (which) {
                    0 -> choosePhotoFromGallery()
                }
            }
            alertDialog.show()
        }
        onlineCard.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setIcon(android.R.drawable.arrow_up_float)
            builder.setTitle(resources.getString(R.string.alert))
            builder.setMessage("感恩有你。")
            builder.show()
        }
        wordCard.setOnClickListener {
            startActivity(wordEditorIntent)
        }
        quizCard.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, QuizActivity::class.java))
        }
        btnLogout.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        }
        sentenceCard.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, SentenceActivity::class.java))
        }
        lockCard.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("网络异常")
            builder.setMessage("网络异常。")
            builder.show()
        }
    }


    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault()) // A date format
//        et_date.setText(sdf.format(cal.time).toString()) // A selected date using format which we have used is set to the UI.
    }

    private fun choosePhotoFromGallery() {

    }
}