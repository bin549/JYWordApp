package com.android.jywordapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.android.jywordapp.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var wordCard : CardView = findViewById(R.id.wordCard);
        var quizCard : CardView = findViewById(R.id.quizCard);
        wordCard.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, WordEditor::class.java))
        }
        quizCard.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, QuizActivity::class.java))
        }



    }


}