package com.example.housemanagment.presentation.pages.reportPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentReportBinding
import com.example.housemanagment.models.buildingData.Building
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.fetchResult
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.loadAnimation
import com.example.housemanagment.utils.extension.visible
import com.example.housemanagment.vm.buildings.BuildingViewModel
import kotlinx.coroutines.launch

class ReportFragment : BasePage(R.layout.fragment_report) {
    private var _binding:FragmentReportBinding?=null
    private val binding get() = _binding!!
    private lateinit var genericAdapterDemo: RvGenericAdapter<DemoMenu>
    private lateinit var genericAdapterItem: RvGenericAdapter<Building>
    private val buildingViewModel: BuildingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentReportBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            includeShimmer.linearShimmer.visible()
            includeShimmer.shimmer.startShimmer()

            genericAdapterDemo = RvGenericAdapter(object: RvGenericAdapter.OnItemClickListener<DemoMenu>{
                override fun onItemClick(demoMenu: DemoMenu, position: Int, layoutRes: Int) {
                    appCompositionRoot.screenNavigator.createScreenDocument(demoMenu)
                }

            },R.layout.item_home_menu,getDemoMenu(),appCompositionRoot.mContext){t->}
            rvData.adapter = genericAdapterDemo
        }
    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        getData(appTheme1,false)

        binding.apply {
            swipeRefresh.setOnRefreshListener {
                getData(appTheme1,true)
            }
            binding.includeShimmer.linearShimmer.setBackgroundColor(appTheme1.backgroundColorApp(requireContext()))
            binding.consReport.setBackgroundColor(appTheme1.backgroundColorApp(requireContext()))
            binding.textView.setTextColor(appTheme1.textColorApp(requireContext()))
            binding.consTool.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
        }
    }



    fun getData(appTheme: com.example.housemanagment.uiTheme.AppTheme,isRefresh:Boolean){
        binding.includeShimmer.linearShimmer.visible()
        binding.includeShimmer.shimmer.startShimmer()
        binding.apply {
            launch {
                buildingViewModel.getDataBuilding()
                buildingViewModel.buildingData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                    includeShimmer.shimmer.stopShimmer()
                    includeShimmer.linearShimmer.gone()
                    if (result?.success?.list?.isEmpty() == true) binding.includeApp.lottie.visible()
                    genericAdapterItem = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<Building>{
                        override fun onItemClick(demoMenu: Building, position: Int, layoutRes: Int) {
                            appCompositionRoot.screenNavigator.createCompany(demoMenu)
                        }
                    },R.layout.item_place,result?.success?.list as ArrayList<Building>,appCompositionRoot.mContext,appTheme){t->}
                    rvInfo.adapter = genericAdapterItem
                    swipeRefresh.isRefreshing = false
                }
            }
            if (!isRefresh) {
                viewCons.loadAnimation(appCompositionRoot.mContext).setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {

                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }
                })
            }
            viewCons.visible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}