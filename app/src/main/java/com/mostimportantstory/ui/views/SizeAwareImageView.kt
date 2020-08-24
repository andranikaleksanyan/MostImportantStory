package com.mostimportantstory.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.widget.ImageView


/**
 * Created by Karo.Hovhannisyan on 16, June, 2020
 **/
class SizeAwareImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {


    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val f = FloatArray(9)
        imageMatrix.getValues(f)

        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        val d = drawable
        val origW = d.intrinsicWidth
        val origH = d.intrinsicHeight

        val actW = Math.round(origW * scaleX)
        val actH = Math.round(origH * scaleY)
    }
}