package com.mostimportantstory.data

import com.google.gson.annotations.SerializedName


/**
 * Created by Karo.Hovhannisyan on 16, June, 2020
 **/

data class ConfigBaseData (
    @SerializedName("scenes") val scenes : List<List<Scenes>>
)

data class Scenes (
    @SerializedName("text") val text : String,
    @SerializedName("dots_coordinates") val dots_coordinates : Dots_coordinates,
    @SerializedName("bubble_coordinates") val bubble_coordinates : Bubble_coordinates
)

data class Bubble_coordinates (
    @SerializedName("x") val x : Int,
    @SerializedName("y") val y : Int,
    @SerializedName("type") val type : Int
)

data class Dots_coordinates (
    @SerializedName("x") val x : Int,
    @SerializedName("y") val y : Int
)

