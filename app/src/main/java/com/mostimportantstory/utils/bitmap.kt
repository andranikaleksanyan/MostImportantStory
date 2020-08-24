package com.mostimportantstory.utils

import android.graphics.Bitmap
import android.graphics.Color


/**
 * Created by Karo.Hovhannisyan on 16, June, 2020
 **/


val Bitmap.average: Int
    get() {
        var redBucket = 0
        var greenBucket = 0
        var blueBucket = 0
        var pixelCount = 0

        for (y in 0 until this.getHeight()) {
            for (x in 0 until this.getWidth()) {
                val c = this.getPixel(x, y)

                pixelCount++
                redBucket += Color.red(c)
                greenBucket += Color.green(c)
                blueBucket += Color.blue(c)
            }
        }

        return Color.rgb(
            redBucket / pixelCount,
            greenBucket / pixelCount,
            blueBucket / pixelCount
        )
    }