package com.mostimportantstory.utils

import android.app.Application
import android.media.MediaPlayer
import androidx.annotation.RawRes

/**
 * Created by Karo.Hovhannisyan on 21, June, 2020
 **/

object MisPlayer {

    var soundCompletionListener: (() -> Unit)? = null
    var musicCompletionListener: (() -> Unit)? = null

    private lateinit var applicationContext: Application

    private var soundPlayer: MediaPlayer? = null
    private var musicPlayer: MediaPlayer? = null

    fun init(context: Application) {
        applicationContext = context
    }

    fun playSound(@RawRes rawResId: Int): MisPlayer {
        val assetFileDescriptor =
            applicationContext.resources.openRawResourceFd(rawResId) ?: return this
        soundPlayer = buildSoundPlayer()

        if (soundPlayer?.isPlaying!!)
            soundPlayer?.reset()

        soundPlayer?.run {
            setDataSource(
                assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset,
                assetFileDescriptor.declaredLength
            )
        }

        soundPlayer?.apply {
            prepare()
            start()
        }
        return this
    }

    fun playMusic(@RawRes rawResId: Int): MisPlayer {
        val assetFileDescriptor =
            applicationContext.resources.openRawResourceFd(rawResId) ?: return this
        musicPlayer = buildMusicPlayer()

        musicPlayer?.reset()

        musicPlayer?.run {
            setDataSource(
                assetFileDescriptor.fileDescriptor, assetFileDescriptor.startOffset,
                assetFileDescriptor.declaredLength
            )
        }

        musicPlayer?.apply {
            prepare()
            start()
            isLooping = true

        }
        return this
    }

    fun buildSoundPlayer(): MediaPlayer? {
        if (soundPlayer == null) {
            soundPlayer = MediaPlayer()
            soundPlayer?.apply {
                soundPlayer?.setVolume(1F, 1F)
                setOnPreparedListener { start() }
                setOnCompletionListener {
                    reset()
                    soundCompletionListener?.invoke()
                }
            }
        }
        return soundPlayer
    }

    fun buildMusicPlayer(): MediaPlayer? {
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer()
            musicPlayer?.apply {
                isLooping = true
                musicPlayer?.setVolume(0.3F, 0.3F)
                setOnPreparedListener { start() }
                setOnCompletionListener {
                    reset()
                    musicCompletionListener?.invoke()
                }
            }
        }
        return musicPlayer
    }

    fun pause() {
        if (soundPlayer != null && soundPlayer!!.isPlaying)
            soundPlayer!!.pause()
        if (musicPlayer != null && musicPlayer!!.isPlaying)
            musicPlayer!!.pause()
    }

    fun resume() {
        if (soundPlayer != null && !soundPlayer!!.isPlaying && soundPlayer!!.currentPosition >= 1)
            soundPlayer!!.start()
        if (musicPlayer != null && !musicPlayer!!.isPlaying && musicPlayer!!.currentPosition >= 1)
            musicPlayer!!.start()
    }

    fun stopSound() {
        if (soundPlayer != null) {
            soundPlayer!!.reset()
        }
    }

    fun stopMusic() {
        if (musicPlayer != null) {
            musicPlayer!!.reset()
            musicPlayer = null
        }
    }

    fun muteSoundPlayer() {
        if (soundPlayer == null)
            buildSoundPlayer()
        soundPlayer?.setVolume(0F, 0F)
    }

    fun unmuteSoundPlayer() {
        soundPlayer?.setVolume(1F, 1F)
    }
}
