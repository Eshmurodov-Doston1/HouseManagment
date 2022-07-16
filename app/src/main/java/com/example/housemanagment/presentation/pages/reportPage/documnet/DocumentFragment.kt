package com.example.housemanagment.presentation.pages.reportPage.documnet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentDocumentBinding
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.models.demoMenu.place.PlaceData
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.THREE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.isNotNullOrEmpty
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.utils.extension.visible
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "demoMenu"
class DocumentFragment : BasePage(R.layout.fragment_document) {

    private var demoMenu: DemoMenu? = null
    private val compositionRoot get() = (activity as MainActivity).appCompositionRoot
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            demoMenu = it.getSerializable(ARG_PARAM1) as DemoMenu
        }
    }


    private var _binding:FragmentDocumentBinding?=null
    private val binding get() = _binding!!
    private lateinit var rvGenericAdapter: RvGenericAdapter<PlaceData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentDocumentBinding.inflate(inflater,container,false)
        //setupBarChart()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            when(demoMenu?.cateGory){
                in ZERO..ONE->{
                    rvGenericAdapter = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<PlaceData>{
                        override fun onItemClick(demoMenu: PlaceData, position: Int, layoutRes: Int) {
                            compositionRoot.screenNavigator.createCompanyWithDocument(demoMenu)
                        }
                    },R.layout.item_place,getPlaceData(),compositionRoot.mContext){t->}
                    rv.adapter = rvGenericAdapter
                    searChAllHome()
                }
                TWO->{

                }
                THREE->{

                }
            }


            include.shimmer.startShimmer()
            Handler(Looper.getMainLooper()).postDelayed({
                include.shimmer.stopShimmer()
                consToolbar.visible()
                rv.visible()
                include.consSimmer.gone()
            },2000)



            back.setOnClickListener {
                compositionRoot.screenNavigator.popBackStack()
            }
            toolbarText.textApp(demoMenu?.title.toString())
            searchButton.setOnClickListener {
                toolbarText.gone()
                searchButton.gone()
                back.gone()
                searchLinear.visible()
              compositionRoot.inputTextCreateKeyboard(search)
            }
            clearText.setOnClickListener {
                if (search.text.toString().isNotNullOrEmpty()){
                    search.text.clear()
                }else{
                    toolbarText.visible()
                    searchButton.visible()
                    back.visible()
                    searchLinear.gone()
                    compositionRoot.hideKeyboard(search)
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
        var listData = ArrayList<PlaceData>()
        if (str.isNotNullOrEmpty()){
            getPlaceData().forEach { placeData ->
                if (placeData.name.trim().lowercase().contains(str.lowercase())){
                    listData.add(placeData)
                }
            }
        }
        if (str.isNotNullOrEmpty()){
            rvGenericAdapter.filterData(listData)
        }else{
            rvGenericAdapter.filterData(getPlaceData())
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