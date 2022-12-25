package com.android.jywordapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import com.android.jywordapp.Constants
import com.android.jywordapp.R
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.android.jywordapp.WordApp
import com.android.jywordapp.model.Exam
import kotlinx.coroutines.launch
import java.time.ZoneId

class ProfileActivity : AppCompatActivity() {
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var cal = Calendar.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val wordEditorIntent = Intent(this@ProfileActivity, WordEditor::class.java)
        wordEditorIntent.putExtra(Constants.USER_ID, intent.getIntExtra(Constants.USER_ID,0))
        var ivCountdown: ImageView = findViewById(R.id.iv_countdown)
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
        val userId = intent.getIntExtra(Constants.USER_ID, 0)
        val wordDao = (application as WordApp).db.wordDao()
        lifecycleScope.launch {
            wordDao.fetchWordsByIsKnown(userId, 1).collect() {
                val list = ArrayList(it)
                tvVocabularyQuantity.text = list.size.toString() + "个"
            }
        }
        var exam: Exam = Constants.getExams().get(0)
        val dateNow = Calendar.getInstance().time
        var examTime = Date.from(exam.date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        var leftTime = examTime.time - dateNow.time
        tvCountdownWord.text = "距离" + exam.name
        tvCountdownTime.text = "还剩下" +(leftTime/(60*60*24*1000)).toString()+"天"

        tvOnline.text = "累计在线"
        tvOnlineQuantity.text = "42天"
        tvCountdownDescription.text = "倒计时"
        tvQuiz.text = "词汇小测"
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
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
//        et_date.setText(sdf.format(cal.time).toString()) // A selected date using format which we have used is set to the UI.
    }

    private fun choosePhotoFromGallery() {

    }
}