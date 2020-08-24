package com.mostimportantstory.data
import com.google.gson.annotations.SerializedName


/**
 * Created by Karo.Hovhannisyan on 11, July, 2020
 **/

data class ConfigTellStory(
    @SerializedName("tell_story_scenes")
    val tellStoryScenes: List<TellStoryScene>
)

data class TellStoryScene(
    @SerializedName("image_res_id")
    val imageResId: String,
    val story: List<Story>,
    val title: String
)

data class Story(
    val text: String
)