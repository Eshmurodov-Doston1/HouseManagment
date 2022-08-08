package com.example.housemanagment.presentation.pages.reportPage.documnet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.adapters.rvAdapter.adapterFlat.ChildAdapterFlat
import com.example.housemanagment.databinding.FragmentDocumentBinding
import com.example.housemanagment.models.buildingData.Building
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.models.house.House
import com.example.housemanagment.models.soldData.Sold
import com.example.housemanagment.models.soldData.SoldData
import com.example.housemanagment.models.valutaCourse.CourseData
import com.example.housemanagment.models.valutaCourse.CourseDataItem
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.EMPTY
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.THREE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.*
import com.example.housemanagment.vm.buildings.BuildingViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
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
    private lateinit var rvGenericAdapterCourseData: RvGenericAdapter<CourseDataItem>
    private lateinit var rvAgreement: RvGenericAdapter<Sold>
    private lateinit var childAdapterFlat:ChildAdapterFlat
    private lateinit var listCurrency: ArrayList<CourseDataItem>
    private lateinit var listAgreement: ArrayList<Sold>
    val gson:Gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentDocumentBinding.inflate(inflater,container,false)
        listCurrency = ArrayList()
        listBuilding = ArrayList()
        listAgreement = ArrayList()
        bindingView = binding
        return binding.root
    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        binding.apply {
            when(demoMenu?.cateGory){
                ZERO->{
                    buildingViewModel.getDataBuilding()
                    include.consSimmer.visible()
                    include.shimmer.startShimmer()
                    launch {
                        buildingViewModel.buildingData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                            listBuilding = result?.success?.list as ArrayList<Building>
                            if (result.success.list.isEmpty()) binding.includeApp.lottie.visible()
                            if(listBuilding.isEmpty()) includeApp.lottie.visible()
                            rvGenericAdapter = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<Building>{
                                override fun onItemClick(demoMenu: Building, position: Int, layoutRes: Int) {
                                    appCompositionRoot.screenNavigator.createCompanyWithDocument(demoMenu)
                                }
                            },R.layout.item_place,listBuilding,appCompositionRoot.mContext,appTheme1){ t->}
                            rv.adapter = rvGenericAdapter
                            searChAllHome()
                            include.shimmer.stopShimmer()
                            consToolbar.visible()
                            rv.visible()
                            include.consSimmer.gone()
                        }
                    }

                }
                ONE->{
                     includeShim.consShimmer.visible()
                        buildingViewModel.getDataBuilding()
                        launch {
                            buildingViewModel.getSoldHouse()
                            buildingViewModel.houseData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                                if (result?.success?.list?.isEmpty() == true) binding.includeApp.lottie.visible()
                                childAdapterFlat = ChildAdapterFlat(result?.success?.list as ArrayList<House>,object:ChildAdapterFlat.OnItemClickListener{
                                    override fun onItemClick(house: House, position: Int) {
                                        val houseJson = gson.toJson(house)
                                        appCompositionRoot.screenNavigator.createFragmentFlatData(houseJson, ONE)
                                    }

                                    override fun onItemClickCall(house: House, position: Int) {
                                        appCompositionRoot.call("+${house.phone}")
                                    }

                                    override fun onItemClickListenerBooking(
                                        house: House,
                                        position: Int,
                                        isPrice:Boolean
                                    ) {

                                    }
                                },appCompositionRoot,appTheme1, docPos = 1)
                                rv.adapter = childAdapterFlat
                                includeShim.consShimmer.gone()
                                consToolbar.visible()
                                rv.visible()
                            }
                        }
                    }
                TWO->{
                    includeShim.consShimmer.visible()
                    includeShim.shimmer.startShimmer()
                    buildingViewModel.soldData()
                    search.doAfterTextChanged {
                        if (it.toString().isNotNullOrEmpty()) searchAgreement(it.toString()) else searchAgreement(EMPTY)
                    }
                    launch {
                        listAgreement = ArrayList()
                        buildingViewModel.soldData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                            listAgreement.addAll(result?.list?: emptyList())
                            if (result?.list?.isEmpty() == true) binding.includeApp.lottie.visible()
                            rvAgreement = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<Sold>{
                                override fun onItemClick(
                                    sold: Sold,
                                    position: Int,
                                    layoutRes: Int
                                ) {
                                   appCompositionRoot.screenNavigator.createAgreement(sold)
                                }
                            },R.layout.item_agreement_flat,listAgreement,requireContext(),appTheme1){posData->
                                appCompositionRoot.call("+${posData.client?.phone}")
                            }

                            consToolbar.visible()
                            rv.visible()
                            includeShim.consShimmer.gone()
                            includeShim.shimmer.stopShimmer()
                            rv.adapter = rvAgreement
                        }
                    }

                }
                THREE->{
                    includeCourse.shimmerCourse.visible()
                    includeCourse.shimmer.startShimmer()
                    search.doAfterTextChanged {
                        if (it.toString().isNotNullOrEmpty()) searchDataCurrency(it.toString()) else searchDataCurrency(EMPTY)
                    }


                    buildingViewModel.getCbuData()
                    launch {
                        buildingViewModel.courseData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                            listCurrency.addAll(result as ArrayList<CourseDataItem>)
                            if (result.isEmpty()) binding.includeApp.lottie.visible()
                           rvGenericAdapterCourseData = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<CourseDataItem>{
                               override fun onItemClick(
                                   demoMenu: CourseDataItem,
                                   position: Int,
                                   layoutRes: Int
                               ) {

                               }
                           },R.layout.item_course,listCurrency,requireContext(),appTheme1){data->}
                            rv.adapter = rvGenericAdapterCourseData
                            consToolbar.visible()
                            rv.visible()
                            includeCourse.shimmerCourse.gone()
                            includeCourse.shimmer.stopShimmer()
                        }
                    }
                    OverScrollDecoratorHelper.setUpOverScroll(rv, OverScrollDecoratorHelper.ORIENTATION_VERTICAL)
                }
            }
        }
    }


    fun searchDataCurrency(text:String){
        var listCurrencySearch = ArrayList<CourseDataItem>()
        listCurrency.onEach {
            if (it.CcyNm_RU.lowercase().contains(text.lowercase()) || it.Ccy.lowercase().contains(text.lowercase())){
                listCurrencySearch.add(it)
            }
        }
        if (listCurrencySearch.isNotEmpty()){
            rvGenericAdapterCourseData.filterData(listCurrencySearch)
        }else{
            rvGenericAdapterCourseData.filterData(listCurrency)
        }
    }


    fun searchAgreement(text:String){
        var listAgreementSearch = ArrayList<Sold>()
        listAgreement.onEach {
            if (it.building?.name?.lowercase()?.contains(text.lowercase()) == true ||
                it.blok?.name?.lowercase()?.contains(text.lowercase()) == true ||
                it.house?.number?.lowercase()?.contains(text.lowercase()) == true ||
                it.dom?.name?.lowercase()?.contains(text.lowercase()) == true){
                listAgreementSearch.add(it)
            }
        }
        if (listAgreementSearch.isNotEmpty()){
            rvAgreement.filterData(listAgreementSearch)
        }else{
            rvAgreement.filterData(listAgreement)
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