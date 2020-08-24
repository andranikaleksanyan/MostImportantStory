package com.mostimportantstory.data.config.model

import android.app.Application
import com.google.gson.Gson
import com.mostimportantstory.data.ConfigBaseData
import com.mostimportantstory.data.ConfigTellStory
import org.json.JSONObject
import java.nio.charset.Charset


/**
 * Created by Karo.Hovhannisyan on 16, June, 2020
 **/
object ConfigModel {
    const val CONFIG_JSON = "dot-coordinates.json"
    const val CONFIG_TELL_STORY_JSON = "config_tell_story.json"

    fun getConfigFile(context: Application, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        inputStream.close()

        return String(buffer, Charset.forName("UTF-8"))
    }

    suspend fun getConfigJson(context: Application, fileName: String): JSONObject {
        return JSONObject(getConfigFile(context, fileName))
    }

    suspend fun convertBaseConfigJsonToObjcet(context: Application): ConfigBaseData {
        return Gson().fromJson(
            getConfigFile(
                context,
                CONFIG_JSON
            ), ConfigBaseData::class.java
        )
    }

    suspend fun convertTellStoryConfigJsonToObjcet(context: Application): ConfigTellStory {
        return Gson().fromJson(
            getConfigFile(
                context,
                CONFIG_TELL_STORY_JSON
            ), ConfigTellStory::class.java
        )
    }
}