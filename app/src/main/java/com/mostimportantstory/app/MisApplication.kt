package com.mostimportantstory.app

import android.app.Application
import com.mostimportantstory.utils.MisPlayer

/**
 * Created by Karo.Hovhannisyan on 21, June, 2020
 **/
class MisApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MisPlayer.init(this)
    }
}