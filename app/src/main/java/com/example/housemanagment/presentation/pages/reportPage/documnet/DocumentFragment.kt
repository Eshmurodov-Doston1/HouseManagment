package com.example.housemanagment.presentation.pages.reportPage.documnet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.adapters.rvAdapter.adapterFlat.ChildAdapterFlat
import com.example.housemanagment.databinding.FragmentDocumentBinding
import com.example.housemanagment.models.buildingData.Building
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.models.house.House
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.*
import com.example.housemanagment.vm.buildings.BuildingViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "demoMenu"
class DocumentFragment : BasePage(R.layout.fragment_document) {
    private var demoMenu: DemoMenu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            demoMenu = it.getSerializable(ARG_PARAM1) as DemoMenu
        }
    }


    private var _binding:FragmentDocumentBinding?=null
    private val binding get() = _binding!!
    private val buildingViewModel:BuildingViewModel by viewModels()
    private lateinit var listBuilding:ArrayList<Building>
    private lateinit var rvGenericAdapter: RvGenericAdapter<Building>
    private lateinit var childAdapterFlat:ChildAdapterFlat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentDocumentBinding.inflate(inflater,container,false)
        listBuilding = ArrayList()
        bindingView = binding
        return binding.root
    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        binding.apply {
            when(demoMenu?.cateGory){
                ZERO->{
                    launch {
                        buildingViewModel.getDataBuilding()
                        includeShim.consShimmer.gone()
                        buildingViewModel.buildingData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                            listBuilding = result?.success?.list as ArrayList<Building>
                            if(listBuilding.isEmpty()) includeApp.lottie.visible()
                            rvGenericAdapter = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<Building>{
                                override fun onItemClick(demoMenu: Building, position: Int, layoutRes: Int) {
                                    appCompositionRoot.screenNavigator.createCompanyWithDocument(demoMenu)
                                }
                            },R.layout.item_place,listBuilding,appCompositionRoot.mContext,appTheme1){ t->}
                            rv.adapter = rvGenericAdapter
                            searChAllHome()
                        }
                    }
                    include.shimmer.stopShimmer()
                    consToolbar.visible()
                    rv.visible()
                    include.consSimmer.gone()
                }
                    ONE->{
                     include.consSimmer.gone()
                     includeShim.consShimmer.visible()
                        buildingViewModel.getDataBuilding()
                        launch {
                            buildingViewModel.getSoldHouse()
                            buildingViewModel.houseData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                                childAdapterFlat = ChildAdapterFlat(result?.success?.list as ArrayList<House>,object:ChildAdapterFlat.OnItemClickListener{
                                    override fun onItemClick(house: House, position: Int) {
                                        Log.e("HouseData", house.toString())
                                        appCompositionRoot.screenNavigator.createFragmentFlatData(house, ONE)
                                    }

                                    override fun onItemClickCall(house: House, position: Int) {
                                        appCompositionRoot.call("+998901277233")
                                    }

                                    override fun onItemClickCallSMS(house: House, position: Int) {

                                    }
                                },appCompositionRoot,appTheme1, docPos = 1)
                                rv.adapter = childAdapterFlat
                                includeShim.consShimmer.gone()
                                consToolbar.visible()
                                rv.visible()
                            }
                        }
                    }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            include.shimmer.startShimmer()



            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }
            toolbarText.textApp(demoMenu?.title.toString())
            searchButton.setOnClickListener {
                toolbarText.gone()
                searchButton.gone()
                back.gone()
                searchLinear.visible()
              appCompositionRoot.inputTextCreateKeyboard(search)
            }
            clearText.setOnClickListener {
                if (search.text.toString().isNotNullOrEmpty()){
                    search.text.clear()
                }else{
                    toolbarText.visible()
                    searchButton.visible()
                    back.visible()
                    searchLinear.gone()
                    appCompositionRoot.hideKeyboard(search)
                }
            }



        }
    }

    private fun searChAllHome() {
        binding.apply {
            search.addTextChangedListener {
              filterApp(it.toString().trim())
            }


        }
    }

    fun filterApp(str:String){
        var listData = ArrayList<Building>()
        if (str.isNotNullOrEmpty()){
            listBuilding.forEach { building ->
                if (building.name.trim().lowercase().contains(str.lowercase())){
                    listData.add(building)
                }
            }
        }
        if (str.isNotNullOrEmpty()){
            rvGenericAdapter.filterData(listData)
        }else{
            rvGenericAdapter.filterData(listBuilding)
        }
    }

    private fun setupBarChart() {
        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        val data: ArrayList<HashMap<String, Int?>> = arrayListOf()

        repeat(7){
            data.add(hashMapOf(Pair(calendar.time.convertCurrentDateToChartDate(LINE_CHART_DATE_FORMAT),it+1)))
            calendar.add(Calendar.DATE, -1)
        }

       // binding.barChartViewBrushing.animate(data.toChartData())
    }


    private fun Date.convertCurrentDateToChartDate(dataFormat:String): String {
        var day: String
        Calendar.getInstance().apply {
            time = this@convertCurrentDateToChartDate
            day = SimpleDateFormat(dataFormat, Locale.getDefault()).apply {
                timeZone = TimeZone.getDefault()
            }.format(time)
        }
        return day
    }

    companion object {
        private const val LINEAR_CHART_DATE_FORMAT = "yyyy/MM/dd"
        private const val LINE_CHART_DATE_FORMAT = "yyyy-MM-dd"

        private const val CHART_ANIMATION_DURATION = 2500L
    }

}