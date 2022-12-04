package com.android.jywordapp

import android.app.Application
import com.android.jywordapp.Database.WordDatabase

class WordApp : Application() {
    val db by lazy {
        WordDatabase.getInstance(this)
    }
}