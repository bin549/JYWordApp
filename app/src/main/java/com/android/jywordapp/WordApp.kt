package com.android.jywordapp

import android.app.Application

class WordApp : Application() {
    val db by lazy {
        WordDatabase.getInstance(this)
    }
}