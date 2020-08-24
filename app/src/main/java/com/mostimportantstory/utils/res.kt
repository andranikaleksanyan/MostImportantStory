package com.mostimportantstory.utils

import android.app.Application
import android.content.res.Resources

/**
 * Created by Karo.Hovhannisyan on 28, June, 2020
 **/

fun Resources.getDrawable(drawableName: String, context: Application) = this.getIdentifier(
    drawableName,
    "drawable",
    context.packageName
)

fun Resources.getRaw(rawName: String, context: Application) = this.getIdentifier(
    rawName,
    "raw",
    context.packageName
)

fun Resources.getArray(arrayName: String, context: Application) = this.getIdentifier(
    arrayName,
    "array",
    context.packageName
)