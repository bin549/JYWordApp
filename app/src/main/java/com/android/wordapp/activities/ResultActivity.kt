package com.android.wordapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.wordapp.Constants
import com.android.wordapp.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val userName = intent.getStringExtra(Constants.USER_Name)
        val correctCount = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalCount = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 0)
        val ivResult: ImageView = findViewById(R.id.iv_result)
        val tvPlayer: TextView = findViewById(R.id.tv_player)
        val tvScore: TextView = findViewById(R.id.tv_score)
        val btnFinish: Button = findViewById(R.id.btn_finish)
        tvPlayer.text ="你答对了" + correctCount.toString() + "题"
        if (correctCount == totalCount) {
            tvPlayer.text ="你全答对了"
            ivResult.setImageURI(Uri.parse("android.resource://com.android.jywordapp/" + R.drawable.best))
        }else if (correctCount == 0) {
            tvPlayer.text ="你全答错了"
            ivResult.setImageURI(Uri.parse("android.resource://com.android.jywordapp/" + R.drawable.suck))
        } else if (correctCount > totalCount / 2) {
            tvScore.text =  "恭喜你:" + userName + "🤩"
            ivResult.setImageURI(Uri.parse("android.resource://com.android.jywordapp/" + R.drawable.good))
        } else {
            tvScore.text = "继续努力吧！" + userName + "🥴"
            ivResult.setImageURI(Uri.parse("android.resource://com.android.jywordapp/" + R.drawable.bad))
        }
        btnFinish.setOnClickListener {
            startActivity(Intent(this@ResultActivity, ProfileActivity::class.java))
        }
    }
}