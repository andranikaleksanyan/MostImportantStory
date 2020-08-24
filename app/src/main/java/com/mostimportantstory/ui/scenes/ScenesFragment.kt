package com.mostimportantstory.ui.scenes

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mostimportantstory.R
import com.mostimportantstory.data.model.Chapter
import com.mostimportantstory.databinding.FragmentScenesBinding
import com.mostimportantstory.ui.scene.StoryType
import com.mostimportantstory.ui.scenes.adapter.ScenesAdapter
import com.mostimportantstory.ui.scenes.viewmodel.SceneViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ScenesFragment : Fragment(), View.OnTouchListener {

    private lateinit var b: FragmentScenesBinding
    private lateinit var navController: NavController

    private val viewmodel: SceneViewModel by activityViewModels()

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: ScenesAdapter
    private lateinit var parcable: Parcelable


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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_scenes, container, false)
        b.fragment = this
        configureAdapter()
        b.rvScenes.layoutManager?.onRestoreInstanceState(parcable)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        viewmodel.adapterPosition.observe(requireActivity(), Observer {
            scrollRecycler(11)
        })
    }

    override fun onStop() {
        super.onStop()
        parcable = b.rvScenes.layoutManager?.onSaveInstanceState()!!


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureAdapter() {
        b.ivBackground.post {
            adapter = ScenesAdapter((b.ivBackground.width - (b.ivBackground.width / 10)) / 3)

            val listOfChapter = listOf(
                Chapter(R.drawable.selector_chapter_0, 5),
                Chapter(R.drawable.selector_chapter_3, 16),
                Chapter(R.drawable.selector_chapter_1, 7),
                Chapter(R.drawable.selector_chapter_4, 17),
                Chapter(R.drawable.selector_chapter_2, 14),
                Chapter(R.drawable.selector_chapter_5, 20),
                Chapter(R.drawable.selector_chapter_6, 21),
                Chapter(R.drawable.selector_chapter_9, 27),
                Chapter(R.drawable.selector_chapter_7, 23),
                Chapter(R.drawable.selector_chapter_10, 28),
                Chapter(R.drawable.selector_chapter_8, 25),
                Chapter(R.drawable.selector_chapter_11, 31)
            )

            adapter.submitList(listOfChapter)

            val gridLayoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
            b.rvScenes.layoutManager = gridLayoutManager
            LinearSnapHelper().attachToRecyclerView(b.rvScenes)
            b.rvScenes.setOnTouchListener(this)
            b.rvScenes.adapter = adapter
            handleClick()
        }
    }

    private fun handleClick() {
        adapter.onItemClicked = {

            viewmodel.saveAdapterPosition(b.rvScenes.computeHorizontalScrollOffset())
            navController.navigate(
                ScenesFragmentDirections.actionScenesFragmentToMainSceneFragment(
                    position = it.scenePosition, storyType = StoryType.SCENES
                )
            )
        }
    }

    fun onPrevClick() {
        scrollRecycler(0)
        b.ivNextInv.visibility = View.VISIBLE
        b.ivPrevInv.visibility = View.GONE
    }

    fun onNextClick() {
        scrollRecycler(11)
        b.ivNextInv.visibility = View.GONE
        b.ivPrevInv.visibility = View.VISIBLE
    }

    private fun scrollRecycler(position: Int) {
        b.rvScenes.adapter?.let {
            b.rvScenes.layoutManager?.smoothScrollToPosition(
                b.rvScenes, RecyclerView.State(),
                position
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScenesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return true
    }
}