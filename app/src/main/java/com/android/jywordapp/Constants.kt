package com.android.jywordapp

import com.android.jywordapp.model.Question

object Constants {
    const val USER_ID: String = "user_id"
    const val USER_Name: String = "user_name"
    const val CORRECT_ANSWERS: String = "correct_answers"
    const val TOTAL_QUESTIONS: String = "total_questions"

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()
        val que1 = Question(
            1, "美归.",
            "Argentina", "Australia",
            "Armenia", "Austria", 1
        )
        val que2 = Question(
            2, "奥是",
            "Angola", "Austria",
            "Australia", "Armenia", 3
        )
        val que3 = Question(
            3, "端个?",
            "Belarus", "Belize",
            "Brunei", "Brazil", 4
        )
        val que4 = Question(
            4, "来了?",
            "Bahamas", "Belgium",
            "Barbados", "Belize", 2
        )
        val que5 = Question(
            5, "休闲",
            "Gabon", "France",
            "Fiji", "Finland", 3
        )
        val que6 = Question(
            6, "卢浮",
            "Germany", "Georgia",
            "Greece", "none of these", 1
        )
        val que7 = Question(
            7, "第三",
            "Dominica", "Egypt",
            "Denmark", "Ethiopia", 3
        )
        val que8 = Question(
            8, "包括",
            "Ireland", "Iran",
            "Hungary", "India", 4
        )
        val que9 = Question(
            9, "恶搞",
            "Australia", "New Zealand",
            "Tuvalu", "United States of America", 2
        )
        val que10 = Question(
            10, "客户",
            "Kuwait", "Jordan",
            "Sudan", "Palestine", 1
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
