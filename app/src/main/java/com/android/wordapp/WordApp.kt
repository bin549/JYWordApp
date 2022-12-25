package com.android.wordapp

import android.app.Application
import com.android.wordapp.Database.WordDatabase

class WordApp : Application() {
    val db by lazy {
        WordDatabase.getInstance(this)
    }
}