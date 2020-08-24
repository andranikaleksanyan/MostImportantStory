package com.mostimportantstory.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Created by Karo.Hovhannisyan on 13, June, 2020
 **/

fun Fragment.addChildFragment(fragment: Fragment, @IdRes frameId: Int) {
    childFragmentManager.beginTransaction().replace(frameId, fragment).commit()
}

fun Fragment.getDrawable(drawableName: String) =
    this.resources.getDrawable(drawableName, requireActivity().application)

fun Fragment.getRaw(rawName: String) = this.resources.getRaw(rawName, requireActivity().application)

fun Fragment.getArray(arrayName: String) =
    this.resources.getArray(arrayName, requireActivity().application)