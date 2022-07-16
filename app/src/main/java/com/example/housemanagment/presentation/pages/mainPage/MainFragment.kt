package com.example.housemanagment.presentation.pages.mainPage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.housemanagment.R
import com.example.housemanagment.adapters.viewPagerAdapter.ViewPagerAdapter
import com.example.housemanagment.databinding.FragmentMainBinding
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.*


class MainFragment : BasePage(R.layout.fragment_main) {
    private var _binding:FragmentMainBinding?=null
    private val binding get() = _binding!!
    private val compositionRoot get() = (activity as MainActivity).appCompositionRoot
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        bindingView = binding
        appCompositionRootBase = compositionRoot
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {





            bottomNavigation.setupWithNavController(compositionRoot.mNavController)
            viewPagerAdapter = ViewPagerAdapter(compositionRoot.mActivity)
            viewPager2.adapter = viewPagerAdapter

            viewPager2.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                   bottomNavigation.isSelected(position)
                }
            })

           bottomNavigation.setOnItemSelectedListener {
               viewPager2.currentItem = bottomNavigation.clickData(it.itemId)
               true
           }

        }
    }
}