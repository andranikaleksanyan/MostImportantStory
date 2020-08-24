package com.mostimportantstory.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mostimportantstory.R
import com.mostimportantstory.data.DataHolder
import com.mostimportantstory.databinding.FragmentMainBinding
import com.mostimportantstory.ui.main.viewmodel.MainViewModel
import com.mostimportantstory.utils.LogUtils

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainFragment : Fragment() {

    private lateinit var b: FragmentMainBinding
    lateinit var navController: NavController
    private lateinit var vm: MainViewModel

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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        b.fragment = this
        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        observe()
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun observe() {
        vm.configBaseModel().observe(viewLifecycleOwner, Observer {
            DataHolder.configBaseData = it
        })

        vm.configTellStoryModel().observe(viewLifecycleOwner, Observer {
            DataHolder.configTellStoryData = it
        })
    }

    fun onClick(v: View) {
        when (v.id) {
//            R.id.iv_read_myself -> navController.navigate(R.id.action_welcomeFragment_to_readMyselfFragment)
//            R.id.iv_info -> navController.navigate(R.id.action_welcomeFragment_to_infoFragment)
            R.id.iv_read_forme -> LogUtils.d("iv_read_forme")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}