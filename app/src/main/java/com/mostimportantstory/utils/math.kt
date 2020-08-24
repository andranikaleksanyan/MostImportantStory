package com.mostimportantstory.utils

import com.mostimportantstory.data.DataHolder

/**
 * Created by Karo.Hovhannisyan on 14, June, 2020
 **/


/**
 * return percent by width
 */
val Float.pw: Float
    get() = this * 100 / DataHolder.imgDrawableWidth

/**
 * return percent by height
 */
val Float.ph: Float
    get() = this * 100 / DataHolder.imgDrawableHeight

/**
 * return width by percent
 */
val Float.wp: Float
    get() = (this * DataHolder.imgDrawableWidth / 100)

/**
 * return height by percent
 */
val Float.hp: Float
    get() = this * DataHolder.imgDrawableHeight / 100

/**
 * return width by percent and add  delta.wp (deviceWidth - imageWidth)width by percent
 */
val Float.wpd: Float
    get() = this.wp + (DataHolder.deltaWidth.pw / 2.toFloat()).wp







