package com.example.housemanagment.presentation.pages.comPany

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentCompanyBinding
import com.example.housemanagment.models.blockData.Block
import com.example.housemanagment.models.buildingData.Building
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.*
import com.example.housemanagment.vm.buildings.BuildingViewModel
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "building"

class CompanyFragment : BasePage(R.layout.fragment_company) {
    private var building: Building? = null
    private val buildingViewModel:BuildingViewModel by viewModels()
    private lateinit var listData:ArrayList<Block>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            building = it.getSerializable(ARG_PARAM1) as Building
        }
    }
    private var _binding:FragmentCompanyBinding?=null
    private val binding get() = _binding!!
    private lateinit var genericAdapterPlace: RvGenericAdapter<Block>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater,container,false)
        bindingView = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            include.shimmer.startShimmer()


            searchButton.setOnClickListener {
                searchLinear.visible()
                searchButton.gone()
                textToolbar.gone()
                appCompositionRoot.inputTextCreateKeyboard(search)
            }

            clearText.setOnClickListener {
                if (search.text.toString().isNotNullOrEmpty()){
                    search.text.clear()
                }else{
                    searchButton.visible()
                    textToolbar.visible()
                    searchLinear.gone()
                    appCompositionRoot.hideKeyboard(search)
                }
            }

            textToolbar.textApp(building?.name.toString())

            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }
            searchCompany()
        }

    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        getData(appTheme1,false)
    }

    private fun searchCompany() {
        binding.apply {
            search.addTextChangedListener {
                filterEmploye(it.toString().trim())
            }
        }
    }

    private fun filterEmploye(text: String) {
        var listPlaceData = ArrayList<Block>()
        if (text.isNotNullOrEmpty()){
            listData.onEach { block ->
                if (block.name.lowercase().contains(text.lowercase())){
                    listPlaceData.add(block)
                }
            }
        }
        if (text.isNotNullOrEmpty()){
            genericAdapterPlace.filterData(listPlaceData)
        }else{
            genericAdapterPlace.filterData(listData)
        }
    }


    fun getData(appTheme: com.example.housemanagment.uiTheme.AppTheme,isRefresh:Boolean){
        listData = ArrayList()
        launch {
            buildingViewModel.getDataBuildingBlock(building?.id?:0)
            buildingViewModel.blockData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                listData = result?.success?.list as ArrayList<Block>
                if(listData.isEmpty()) binding.includeApp.lottie.visible()
                genericAdapterPlace = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<Block>{
                    override fun onItemClick(block: Block, position: Int, layoutRes: Int) {
                        appCompositionRoot.screenNavigator.createFlatScreen(block)
                    }
                },R.layout.item_place_company,listData,appCompositionRoot.mContext,appTheme){ t->}
                binding.rvHouse.adapter = genericAdapterPlace

                binding.include.shimmer.stopShimmer()
                binding.consTool.visible()
                binding.rvHouse.visible()
                binding.include.consSimmer.gone()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}