package com.android.jywordapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.jywordapp.R
import com.android.jywordapp.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {
    private var binding: ActivityQuizBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setSupportActionBar(binding?.toolbarQuizActivity)
        setContentView(binding?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "个人空间"
        binding?.toolbarQuizActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}