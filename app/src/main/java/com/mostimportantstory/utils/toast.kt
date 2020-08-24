package com.mostimportantstory.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by Karo.Hovhannisyan on 13, June, 2020
 **/

class ToastUtils : Util() {
    companion object {
        fun t(context: Context, msg: String) {
            if (IS_DEBUGGINIG)
                Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }
}