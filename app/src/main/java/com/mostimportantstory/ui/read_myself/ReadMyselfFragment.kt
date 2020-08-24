package com.mostimportantstory.ui.read_myself

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mostimportantstory.R
import com.mostimportantstory.databinding.FragmentReadMyselfBinding
import com.mostimportantstory.ui.scene.StoryType

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReadMyselfFragment : Fragment() {
    private lateinit var b: FragmentReadMyselfBinding
    lateinit var navController: NavController

    private var param1: String? = null
    private var param2: String? = null

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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_read_myself, container, false)
        b.fragment = this
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_scenes -> {
                navController.navigate(R.id.action_readMyselfFragment_to_scenesFragment)
            }
            R.id.iv_read_story -> {
                navController.navigate(
                    ReadMyselfFragmentDirections.actionReadMyselfFragmentToMainSceneFragment(
                        storyType = StoryType.READ
                    )
                )
            }
            R.id.iv_watch_story -> {
                navController.navigate(R.id.action_readMyselfFragment_to_mainSceneFragment)
            }
            R.id.iv_tell_story -> {
                navController.navigate(R.id.action_readMyselfFragment_to_tellStoryFragment)

            }
            R.id.iv_info -> {
                navController.navigate(R.id.action_readMyselfFragment_to_infoFragment)
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReadMyselfFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}