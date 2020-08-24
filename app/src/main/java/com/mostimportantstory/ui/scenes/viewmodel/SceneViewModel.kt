package com.mostimportantstory.ui.scenes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mostimportantstory.data.model.Chapter

/**
 * Created by Karo.Hovhannisyan on 11, July, 2020
 **/
class SceneViewModel(state: SavedStateHandle) : ViewModel() {

    companion object {
        private const val ADAPTER_POSITION_KEY = "adapter_position_key"
    }

    private val savedStateHandle = state

    private val _adapterPosition: MutableLiveData<Int> =
        savedStateHandle.getLiveData(ADAPTER_POSITION_KEY)

    fun saveAdapterPosition(position: Int) {
        _adapterPosition.value = position
    }

    val adapterPosition: LiveData<Int> = _adapterPosition


//    val chaptersLiveData : LiveData<List<Chapter>> =


}