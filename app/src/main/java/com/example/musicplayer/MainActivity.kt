package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    val playBtn: ImageButton = findViewById(R.id.playBtn)
    val seekbar: SeekBar = findViewById(R.id.seekBar)
    lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaplayer  = MediaPlayer.create(this, R.raw.id)

        seekbar.progress = 0
        seekbar.max = mediaplayer.duration

        playBtn.setOnClickListener {
            if(!mediaplayer.isPlaying) {
                mediaplayer.start()
                playBtn.setImageResource(R.drawable.baseline_pause_24)
            } else {
                mediaplayer.pause()
                playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        }

        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) {
                    mediaplayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                TODO("Not yet implemented")
            }
        })

        runnable = Runnable {
            seekbar.progress = mediaplayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
        mediaplayer.setOnCompletionListener {
            playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            seekbar.progress = 0
        }
    }
}