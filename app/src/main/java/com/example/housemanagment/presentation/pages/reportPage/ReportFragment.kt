package com.example.housemanagment.presentation.pages.reportPage

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentReportBinding
import com.example.housemanagment.models.demoMenu.DemoItem
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.models.demoMenu.place.PlaceData
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.loadAnimation
import com.example.housemanagment.utils.extension.visible


class ReportFragment : BasePage(R.layout.fragment_report) {

    private var _binding:FragmentReportBinding?=null
    private val binding get() = _binding!!
    private lateinit var genericAdapterDemo: RvGenericAdapter<DemoMenu>
    private lateinit var genericAdapterItem: RvGenericAdapter<PlaceData>
    private val compositionRoot get() = (activity as MainActivity).appCompositionRoot
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
            Handler(Looper.getMainLooper()).postDelayed({
                includeShimmer.shimmer.stopShimmer()
                includeShimmer.linearShimmer.gone()
                viewCons.visible()
                viewCons.loadAnimation(compositionRoot.mContext).setAnimationListener(object:
                    Animation.AnimationListener{
                    override fun onAnimationStart(p0: Animation?) {

                    }

                    override fun onAnimationEnd(p0: Animation?) {

                    }

                    override fun onAnimationRepeat(p0: Animation?) {

                    }

                })
            },2000)

            swipeRefresh.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    swipeRefresh.isRefreshing = false
                },2000)
            }

            genericAdapterDemo = RvGenericAdapter(object: RvGenericAdapter.OnItemClickListener<DemoMenu>{
                override fun onItemClick(demoMenu: DemoMenu, position: Int, layoutRes: Int) {
                    compositionRoot.screenNavigator.createScreenDocument(demoMenu)
                }

            },R.layout.item_home_menu,getDemoMenu(),compositionRoot.mContext){t->}
            rvData.adapter = genericAdapterDemo

            genericAdapterItem = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<PlaceData>{
                override fun onItemClick(demoMenu: PlaceData, position: Int, layoutRes: Int) {
                    compositionRoot.screenNavigator.createCompany(demoMenu)
                }
            },R.layout.item_place,getPlaceData(),compositionRoot.mContext){t->}
            rvInfo.adapter = genericAdapterItem

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}