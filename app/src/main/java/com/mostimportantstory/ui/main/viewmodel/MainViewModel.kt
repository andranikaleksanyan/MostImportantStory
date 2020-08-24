package com.mostimportantstory.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mostimportantstory.data.ConfigBaseData
import com.mostimportantstory.data.ConfigTellStory
import com.mostimportantstory.data.config.model.ConfigRepo

/**
 * Created by Karo.Hovhannisyan on 16, June, 2020
 **/
class MainViewModel(var context: Application) : AndroidViewModel(context) {

    fun configBaseModel(): LiveData<ConfigBaseData> = ConfigRepo.getConfigData(context)

    fun configTellStoryModel(): LiveData<ConfigTellStory> =
        ConfigRepo.getTellStoryConfigData(context)
}