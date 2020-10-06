package com.levagency.miziki.controllers.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.levagency.miziki.R
import com.levagency.miziki.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerBinding
    private var player: SimpleExoPlayer? = null
    var playWhenReady = true
    var currentWindow = 0
    var playbackPosition: Long = 0

    override fun onStart() {
        super.onStart()
        if(Util.SDK_INT < 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
//        hideSystemUi()
        if((Util.SDK_INT < 24 || player == null)){
            initializePlayer()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
    }

    override fun onPause() {
        super.onPause()
        if(Util.SDK_INT < 24){
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT < 24){
            releasePlayer()
        }
    }

    fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        binding.player.player = player

        val mediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
        player!!.setMediaItem(mediaItem)

        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare()
    }

    fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }
//    fun hideSystemUi() {
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
//        | View.SYSTEM_UI_FLAG_FULLSCREEN
//        |
//
//    }
}