package com.android.wordapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.wordapp.R
import android.media.MediaPlayer
import android.net.Uri

class SentenceActivity : AppCompatActivity() {
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence)
//        onBackPressed()
        playBGM()

    }

    private fun playBGM() {
        try {
            val soundURI = Uri.parse("android.resource://com.android.wordapp/" + R.raw.prologue)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = true
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}