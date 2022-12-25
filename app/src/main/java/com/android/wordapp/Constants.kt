@file:Suppress("DEPRECATION")

package com.android.wordapp

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.wordapp.model.Exam
import com.android.wordapp.model.Question
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

object Constants {
    const val USER_ID: String = "user_id"
    const val USER_Name: String = "user_name"
    const val CORRECT_ANSWERS: String = "correct_answers"
    const val TOTAL_QUESTIONS: String = "total_questions"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getExams(): ArrayList<Exam> {
        val examList = ArrayList<Exam>()
        val exam1 = Exam(
            1, "全国大学生英语四级考试",
            LocalDate.parse("2023-06-10")
        )
        val exam2 = Exam(
            1, "全国大学生英语六级考试",
            LocalDate.parse("2023-06-10")
        )
        examList.add(exam1)
        examList.add(exam2)
        return examList
    }

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()
        val que1 = Question(
            1, "scuttle",
            "A、疾走", "B、垃圾场",
            "C、废物堆积处", "D、收容所", 1
        )
        val que2 = Question(
            2, "graveyard",
            "A、注意", "B、快点",
            "C、墓地", "D、警觉", 3
        )
        val que3 = Question(
            3, "goofy",
            "A、排水沟", "B、槽",
            "C、贫民区", "D、傻瓜的", 4
        )
        val que4 = Question(
            4, "inexplicably",
            "A、原谅", "B、莫明其妙",
            "C、同意", "D、容忍", 2
        )
        val que5 = Question(
            5, "soared",
            "A、巫术", "B、魔法",
            "C、飙升", "D、讲述", 3
        )
        val que6 = Question(
            6, "seamlessly",
            "A、无缝地", "B、疲劳",
            "C、疲乏", "D、杂役", 1
        )
        val que7 = Question(
            7, "creepers",
            "A、新闻业", "B、新闻工作",
            "C、爬行物", "D、报章杂志", 3
        )
        val que8 = Question(
            8, "consecrated",
            "A、正义", "B、正直",
            "C、公正", "D、奉献", 4
        )
        val que9 = Question(
            9, "vehemently",
            "A、类的", "B、激烈地",
            "C、一般的", "D、属的", 2
        )
        val que10 = Question(
            10, "radix",
            "A、基数", "B、辩论",
            "C、怀疑", "D、阻止", 1
        )
        questionsList.add(que1)
        questionsList.add(que2)
        questionsList.add(que3)
        questionsList.add(que4)
        questionsList.add(que5)
        questionsList.add(que6)
        questionsList.add(que7)
        questionsList.add(que8)
        questionsList.add(que9)
        questionsList.add(que10)
        return questionsList
    }
}
