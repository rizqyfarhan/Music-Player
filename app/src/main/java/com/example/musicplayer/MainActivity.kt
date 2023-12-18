package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.core.os.HandlerCompat

class MainActivity : AppCompatActivity() {

    lateinit var playBtn : ImageButton
    lateinit var seekbar : SeekBar
    lateinit var runnable: Runnable
    private val handler = HandlerCompat.createAsync(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaplayer  = MediaPlayer.create(this, R.raw.id)

        seekbar = findViewById(R.id.seekBar)
        seekbar.progress = 0
        seekbar.max = mediaplayer.duration

        playBtn = findViewById(R.id.playBtn)
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
                if (fromUser) {
                    handler.post {
                        mediaplayer.seekTo(progress)
                    }
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
            handler.post {
                playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
                seekbar.progress = 0
            }
        }
    }
}