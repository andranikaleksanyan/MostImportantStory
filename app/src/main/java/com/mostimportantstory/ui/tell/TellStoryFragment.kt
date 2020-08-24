package com.mostimportantstory.ui.tell

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mostimportantstory.R
import com.mostimportantstory.data.DataHolder
import com.mostimportantstory.data.TellStoryScene
import com.mostimportantstory.data.config.model.ConfigRepo
import com.mostimportantstory.databinding.FragmentTellStoryBinding
import com.mostimportantstory.utils.getDrawable
import kotlin.math.abs

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TellStoryFragment : Fragment(), View.OnTouchListener {

    private lateinit var b: FragmentTellStoryBinding
    private lateinit var navController: NavController

    private lateinit var scenes: List<TellStoryScene>

    private var param1: String? = null
    private var param2: String? = null
    private var mustRepeatFingerAnim = true
    private var x1: Float = 0F
    private var x2: Float = 0F

    private var scenePosition = 0
    private var speechPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_tell_story, container, false)
        b.fragment = this
        b.clRoot.setOnTouchListener(this)
        configureSizes()
        animateFinger()
        return b.root
    }

    fun onHomeClicked() {
        navController.popBackStack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scenes = ConfigRepo.getTellStoryConfigBaseData().tellStoryScenes
        setScene()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        when (p1?.action) {
            MotionEvent.ACTION_DOWN -> {
                x1 = p1.x
                return true
            }
            MotionEvent.ACTION_UP -> {
                x2 = p1.x
                val delta = x2 - x1
                if (abs(delta) > MIN_DISTANCE) {
                    if (x2 > x1) prev()
                    else next()
                }
                return true
            }
        }
        return false
    }

    private fun next() {
        if (isLastElement())
            return
        if (!isLastSpeech())
            ++speechPosition
        else {
            speechPosition = 0
            ++scenePosition
        }
        setScene()
    }

    private fun prev() {
        if (isFirstElement())
            return
        if (!isFirstSpeech())
            --speechPosition
        else {
            --scenePosition
            speechPosition = scenes[scenePosition].story.size - 1
        }
        setScene()
    }

    private fun isFirstSpeech(): Boolean {
        return speechPosition == 0
    }

    private fun isLastSpeech(): Boolean {
        return speechPosition == scenes[scenePosition].story.size - 1
    }

    private fun isFirstScene(): Boolean {
        return scenePosition == 0
    }

    private fun isLastScene(): Boolean {
        return scenePosition == scenes.size - 1
    }

    private fun isLastElement(): Boolean {
        return isLastScene() && isLastSpeech()
    }

    private fun isFirstElement(): Boolean {
        return isFirstScene() && isFirstSpeech()
    }

    private fun setScene() {
        setImage()
        setSpeechText()
    }

    private fun setImage() {
        getDrawable(scenes[scenePosition].imageResId).let {
            if (scenePosition != b.ivScene.tag) {
                b.ivScene.setImageResource(it)
                b.ivScene.animate().scaleX(1.1F).scaleY(1.1F).setDuration(0).withEndAction {
                    b.ivScene.animate().scaleX(1F).scaleY(1F).setDuration(600).start()
                }
                b.ivScene.tag = scenePosition
            }
        }
    }

    private fun setSpeechText() {
        val speechText = scenes[scenePosition].story[speechPosition].text
        b.bmSpeech.setText(speechText)
        b.tvTitle.text = scenes[scenePosition].title
        b.bmSpeech.animate().scaleX(0F).scaleY(0F).setDuration(100).withEndAction {
            b.bmSpeech.animate().scaleX(1F).scaleY(1F).setDuration(300).start()
        }.start()
    }

    private fun animateFinger() {
        b.ivFinger.postDelayed({
            moveToStartingPoint()
        }, if (mustRepeatFingerAnim) 250L else 0L)
    }

    private fun moveToStartingPoint() {
        b.ivFinger.animate()
            .x(DataHolder.imgDrawableWidth)
            .y(DataHolder.imgDrawableHeight / 3)
            .scaleX(1F)
            .scaleY(1F)
            .setDuration(0)
            .withEndAction {
                animateScaleOut()
            }
            .start()
    }

    private fun animateScaleOut() {
        b.ivFinger.animate()
            .scaleX(0.8F)
            .scaleY(0.8F)
            .setDuration(400)
            .withEndAction {
                animateToLeft()
            }
            .start()
    }

    private fun animateToLeft() {
        b.ivFinger.animate()
            .x(DataHolder.deltaWidth / 2)
            .y(DataHolder.imgDrawableHeight / 3)
            .setDuration(1500)
            .withEndAction {
                animateScaleIn()
            }
            .start()
    }

    private fun animateScaleIn() {
        b.ivFinger.animate()
            .scaleX(1F)
            .scaleY(1F)
            .setDuration(300)
            .withEndAction {
                if (mustRepeatFingerAnim) {
                    mustRepeatFingerAnim = false
                    animateFinger()
                } else
                    b.ivFinger.visibility = View.GONE
            }
            .start()
    }

    private fun configureSizes() {
        b.ivScene.post {
            DataHolder.deviceWidth = b.clRoot.width.toFloat()
            DataHolder.deviceHeight = b.clRoot.height.toFloat()

            DataHolder.imgDrawableWidth = b.ivScene.width.toFloat()
            DataHolder.imgDrawableHeight = b.ivScene.height.toFloat()

            DataHolder.deltaWidth = DataHolder.deviceWidth - DataHolder.imgDrawableWidth
        }
    }

    override fun onDetach() {
        super.onDetach()
        val fingerAnimation = b.ivFinger.animation
        if (fingerAnimation != null && fingerAnimation.hasStarted() && !fingerAnimation.hasEnded())
            fingerAnimation.cancel()
    }

    companion object {
        private const val MIN_DISTANCE = 200

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TellStoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}