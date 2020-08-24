package com.mostimportantstory.data.config.model

import android.app.Application
import androidx.lifecycle.LiveData
import com.mostimportantstory.data.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

/**
 * Created by Karo.Hovhannisyan on 16, June, 2020
 **/

object ConfigRepo {

    var configConvertJob: CompletableJob? = null

    var configTellStoryConvertJob: CompletableJob? = null

    fun getConfigData(context: Application): LiveData<ConfigBaseData> {
        configConvertJob = Job()
        return object : LiveData<ConfigBaseData>() {
            override fun onActive() {
                super.onActive()
                configConvertJob?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val configBaseData =
                            ConfigModel.convertBaseConfigJsonToObjcet(
                                context
                            )
                        withContext(Main) {
                            value = configBaseData
                        }
                    }
                }
            }
        }
    }

    fun getTellStoryConfigData(context: Application): LiveData<ConfigTellStory> {
        configTellStoryConvertJob = Job()
        return object : LiveData<ConfigTellStory>() {
            override fun onActive() {
                super.onActive()
                configTellStoryConvertJob?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val configBaseData =
                            ConfigModel.convertTellStoryConfigJsonToObjcet(
                                context
                            )
                        withContext(Main) {
                            value = configBaseData
                        }
                    }
                }
            }
        }
    }

    fun getDotsCoordinates(pagePosition: Int, speechPosition: Int): Dots_coordinates {
        val configBaseData = DataHolder.configBaseData
        return configBaseData.scenes[pagePosition][speechPosition].dots_coordinates
    }

    fun getBubble(pagePosition: Int, speechPosition: Int): Bubble_coordinates {
        val configBaseData = DataHolder.configBaseData
        return configBaseData.scenes[pagePosition][speechPosition].bubble_coordinates
    }

    fun getBubbleMesssage(pagePosition: Int, speechPosition: Int): String {
        val configBaseData = DataHolder.configBaseData
        return configBaseData.scenes[pagePosition][speechPosition].text
    }

    fun getConfigBaseData(): ConfigBaseData {
        return DataHolder.configBaseData
    }

    fun getTellStoryConfigBaseData(): ConfigTellStory {
        return DataHolder.configTellStoryData
    }


}