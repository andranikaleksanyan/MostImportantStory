package com.mostimportantstory.utils

import android.util.Log

/**
 * Created by Karo.Hovhannisyan on 13, June, 2020
 **/
class LogUtils  : Util() {
    companion object {

        @JvmStatic
        fun d(value: String) {
            if (IS_DEBUGGINIG)
                Log.d(TAG, value)
        }

        @JvmStatic
        fun d(tag: String, value: String) {
            if (IS_DEBUGGINIG)
                Log.d("${TAG}_$tag", value)
        }
    }
}


