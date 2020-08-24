package com.mostimportantstory.ui.splash

import android.os.Bundle
import android.os.Handler
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
import com.mostimportantstory.databinding.FragmentSplashBinding
import com.mostimportantstory.ui.main.viewmodel.MainViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SplashFragment : Fragment() {

    private lateinit var vm: MainViewModel
    private lateinit var b: FragmentSplashBinding
    private lateinit var navController: NavController


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
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        b.fragment = this
        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        observe()
        return b.root
    }

    private fun observe() {
        vm.configBaseModel().observe(viewLifecycleOwner, Observer {
            DataHolder.configBaseData = it
        })

        vm.configTellStoryModel().observe(viewLifecycleOwner, Observer {
            DataHolder.configTellStoryData = it
        })

        openApp()
    }

    private fun openApp(){
        Handler().postDelayed({
            navController.navigate(SplashFragmentDirections.actionSplashFragmentToReadMyselfFragment())
        }, DELAY_TIME)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    companion object {
        private const val DELAY_TIME = 2000L

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}