package com.mostimportantstory.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.ImageView

/**
 * Created by Karo.Hovhannisyan on 16, June, 2020
 **/


val ImageView.bitmap: Bitmap
    get() {
        return (this.drawable as BitmapDrawable).bitmap
    }

val ImageView.averageColor: Int
    get() {
        return this.bitmap.average
    }

private const val ZOOM_ANIM_DURATION = 200L

fun View.zoomOutAnim(): ViewPropertyAnimator {
    return this.animate().scaleX(0F).scaleY(0F).setDuration(ZOOM_ANIM_DURATION)
}

fun View.zoomInAnim(): ViewPropertyAnimator {
    return this.animate()
        .scaleX(1F)
        .scaleY(1F)
        .setDuration(ZOOM_ANIM_DURATION)
}

fun View.zoomOutImmediatly() {
    return this.animate()
        .scaleX(0F)
        .scaleY(0F)
        .setDuration(0)
        .start()
}