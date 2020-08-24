package com.mostimportantstory.ui.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mostimportantstory.R
import com.mostimportantstory.data.DataHolder
import com.mostimportantstory.data.Scenes
import com.mostimportantstory.data.config.model.ConfigRepo
import com.mostimportantstory.databinding.FragmentMainSceneBinding
import com.mostimportantstory.ui.views.BubbleMessageView
import com.mostimportantstory.utils.*
import java.util.*
import kotlin.concurrent.timerTask

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

enum class StoryType {
    WATCH, READ, SCENES
}

class MainSceneFragment : Fragment() {

    private lateinit var b: FragmentMainSceneBinding
    private lateinit var navController: NavController

    private lateinit var scenes: List<List<Scenes>>
    private var scenePosition: Int = 0
    private var speechPosition: Int = 0

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var arrowAlphaAnim: Animation
    private var storyType: StoryType = StoryType.WATCH
    private var isPageClosed : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_main_scene, container, false)
        b.fragment = this

        arrowAlphaAnim =
            AnimationUtils.loadAnimation(requireContext().applicationContext, R.anim.alpha_in_out)

        configureSizes()
        configureMargins()
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val args = arguments?.let { MainSceneFragmentArgs.fromBundle(it) } ?: return
        storyType = args.storyType
        when (storyType) {
            StoryType.READ -> MisPlayer.muteSoundPlayer()
            else -> MisPlayer.unmuteSoundPlayer()
        }

        this.scenePosition = args.position

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        scenes = ConfigRepo.getConfigBaseData().scenes
        getCurSpeechDataAndAnimateDot()
    }

    override fun onPause() {
        super.onPause()
        MisPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        MisPlayer.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        isPageClosed = true
        MisPlayer.stopSound()
        MisPlayer.stopMusic()
    }

    fun onHomeClick() {
        when (storyType) {
            StoryType.SCENES -> findNavController().navigate(R.id.action_mainSceneFragment_to_readMyselfFragment)
            else -> navController.popBackStack()
        }
    }

    fun onNextClick() {
        ++scenePosition
        speechPosition = 0
        b.ivDot.visibility = View.VISIBLE
        getCurSpeechDataAndAnimateDot()
    }

    fun onPrevClick() {
        --scenePosition
        speechPosition = 0
        getCurSpeechDataAndAnimateDot()
    }

    private fun stopEverything() {
        MisPlayer.stopSound()
        b.bmDefSpeech.zoomOutImmediatly()
        b.bmRelativeSpeech.zoomOutImmediatly()
        b.ivDot.zoomOutImmediatly()
    }

    private fun getCurSpeechDataAndAnimateDot() {
        stopEverything()
        val dotsCoordinates = scenes[scenePosition][speechPosition].dots_coordinates

        if (speechPosition == 0) {
            getDrawable("scene_$scenePosition").let {
                b.ivScene.setImageResource(it)
                b.ivScene.animate().scaleX(1.1F).scaleY(1.1F).setDuration(0).withEndAction {
                    b.ivScene.animate().scaleX(1F).scaleY(1F).setDuration(600).start()
                }
            }

            var incrementedScenePosition = scenePosition
            getRaw("sc${++incrementedScenePosition}_backmusic").let {
                if (it != 0)
                    MisPlayer.playMusic(it)
            }
        }

//        b.ivDot.post {
//            b.ivDot.animate()
//                .x(dotsCoordinates.x.toFloat().wpd)
//                .y(dotsCoordinates.y.toFloat().hp)
//                .scaleX(1F)
//                .scaleY(1F)
//                .setDuration(0).start()
//        }

        b.ivDot.postDelayed({
            b.ivDot.animate()
                .x(dotsCoordinates.x.toFloat().wpd)
                .y(dotsCoordinates.y.toFloat().hp)
                .scaleX(1F)
                .scaleY(1F)
                .setDuration(0).start()
        }, 100)

        b.ivPrevInv.visibility = if (scenePosition == 0) View.INVISIBLE else View.VISIBLE

        if (isLastScene())
            b.ivNextInv.visibility = View.INVISIBLE
        else {
            b.ivNextInv.visibility = View.VISIBLE
            if (arrowAlphaAnim.hasStarted())
                arrowAlphaAnim.cancel()
        }
    }

    fun onDotClick() {
        b.ivDot.zoomOutAnim().withEndAction {
            showBubbleMsg()
        }
    }

    private fun showBubbleMsg() {
        val bubbleCoordinates = scenes[scenePosition][speechPosition].bubble_coordinates

        if (bubbleCoordinates.type == 0) {
            b.bmRelativeSpeech.animate().scaleX(0F).scaleY(0F).setDuration(0).start()
            b.bmDefSpeech.setText(scenes[scenePosition][speechPosition].text)
        } else {
            b.bmDefSpeech.animate().scaleX(0F).scaleY(0F).setDuration(0).start()
            b.bmRelativeSpeech.animate()
                .x(bubbleCoordinates.x.toFloat().wpd)
                .y(bubbleCoordinates.y.toFloat().hp)
                .scaleX(0F)
                .scaleY(0F)
                .setDuration(0)
                .start()
            b.bmRelativeSpeech.setText(scenes[scenePosition][speechPosition].text)

            when (bubbleCoordinates.type) {
                1 -> setBubbleBackground(1)
                2 -> setBubbleBackground(2)
                3 -> setBubbleBackground(3)
                4 -> setBubbleBackground(4)
                else -> setBubbleBackground(0)
            }
        }

        getRightBubbleTextView().zoomInAnim().withEndAction {
            playSound()
        }
    }

    private fun playSound() {
        var soundI = scenePosition
        var soundJ = speechPosition
        getRaw("sc${++soundI}_sp${++soundJ}").let { it1 ->
            MisPlayer.playSound(it1).soundCompletionListener = {
                hideBubbleSpeech()
            }
        }
    }

    private fun hideBubbleSpeech() {
        getRightBubbleTextView().zoomOutAnim().withEndAction {
            if (isLastSpeech()) {
                ++speechPosition
            } else {
                if (isLastScene())
                //black cover animation
//                    b.ivBlackCover.animate().alpha(1f).setDuration(END_POP_BACK_DEELAY)
//                        .withEndAction {
//                            when (storyType) {
//                                StoryType.SCENES -> findNavController().navigate(R.id.action_mainSceneFragment_to_readMyselfFragment)
//                                else -> navController.popBackStack()
//                            }
//                        }
                    Timer().schedule(timerTask {
                        if (navController.currentDestination?.id == R.id.mainSceneFragment && !isPageClosed) {
                            when (storyType) {
                                StoryType.SCENES -> findNavController().navigate(R.id.action_mainSceneFragment_to_readMyselfFragment)
                                else -> navController.popBackStack()
                            }
                        }
                    }, END_POP_BACK_DEELAY)
                else
                    b.ivNextInv.startAnimation(arrowAlphaAnim)

                b.ivDot.visibility = View.INVISIBLE
            }
            getCurSpeechDataAndAnimateDot()
        }
    }


    private fun getRightBubbleTextView(): BubbleMessageView {
        return if (scenes[scenePosition][speechPosition].bubble_coordinates.type == 0)
            b.bmDefSpeech
        else b.bmRelativeSpeech
    }

    private fun isLastSpeech(): Boolean {
        return scenes[scenePosition].size - 1 > speechPosition
    }

    private fun isLastScene(): Boolean {
        return scenes.size - 1 <= scenePosition
    }


    private fun setBubbleBackground(drawableResId: Int) {
        if (b.bmRelativeSpeech.visibility != View.VISIBLE)
            b.bmRelativeSpeech.visibility = View.VISIBLE
        b.bmRelativeSpeech.drawBubble(drawableResId)
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

    private fun configureMargins() {
        b.ivNextInv.postDelayed({
            b.ivNextInv.layoutParams =
                (b.ivNextInv.layoutParams as ConstraintLayout.LayoutParams).apply {
                    rightMargin = (DataHolder.deltaWidth / 1.8).toInt()
                }
            b.ivPrevInv.layoutParams =
                (b.ivPrevInv.layoutParams as ConstraintLayout.LayoutParams).apply {
                    leftMargin = (DataHolder.deltaWidth / 1.8).toInt()
                }
            b.ivHome.layoutParams =
                (b.ivHome.layoutParams as ConstraintLayout.LayoutParams).apply {
                    leftMargin = (DataHolder.deltaWidth / 1.8).toInt()
                }
        }, 150)
    }

    companion object {

        private const val END_POP_BACK_DEELAY = 2000L

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainSceneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}