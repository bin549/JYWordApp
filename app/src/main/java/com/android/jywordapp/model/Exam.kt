package com.android.jywordapp.model

import java.time.LocalDate

data class Exam(
    val id: Int,
    val name: String,
    val date: LocalDate,
)
