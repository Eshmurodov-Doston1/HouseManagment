package com.example.housemanagment.presentation.pages.house

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentHousesBinding
import com.example.housemanagment.models.blockData.Block
import com.example.housemanagment.models.flat.Flat
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.extension.*
import com.example.housemanagment.vm.buildings.BuildingViewModel
import kotlinx.coroutines.launch

const val PARAM = "block"
class HousesFragment : BasePage(R.layout.fragment_houses) {
    private var block:Block?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.apply {
            block = this?.getSerializable(PARAM) as Block
        }
    }

   var _binding:FragmentHousesBinding?=null
    val binding get() = _binding!!
    private val buildingViewModel:BuildingViewModel by viewModels()
    private lateinit var rvGenericAdapter:RvGenericAdapter<Flat>
    private lateinit var listDataFlat:ArrayList<Flat>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHousesBinding.inflate(inflater,container,false)
        bindingView = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            include.consSimmer.visible()
            include.shimmer.startShimmer()
            textToolbar.textApp(block?.name.toString())
            buildingViewModel.getDataBuilding()

            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }

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
            searchCompany()
        }
    }

    private fun searchCompany() {
        binding.apply {
            search.addTextChangedListener {
                filterFlat(it.toString().trim())
            }
        }
    }


    private fun filterFlat(text: String) {
        var listFlat = ArrayList<Flat>()
        if (text.isNotNullOrEmpty()){
            listDataFlat.onEach { block ->
                if (block.name.lowercase().contains(text.lowercase())){
                    listFlat.add(block)
                }
            }
        }
        if (text.isNotNullOrEmpty()){
            rvGenericAdapter.filterData(listFlat)
        }else{
            rvGenericAdapter.filterData(listDataFlat)
        }
    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
       getFlatData(appTheme1)
    }
    fun getFlatData(appTheme1:com.example.housemanagment.uiTheme.AppTheme){
        listDataFlat= ArrayList()
        launch {
            buildingViewModel.getFlatData(block?.id?:0)
            buildingViewModel.flatData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                listDataFlat = result?.success?.list as ArrayList<Flat>
                if (listDataFlat.isEmpty()){
                    binding.includeApp.lottie.visible()
                }
                rvGenericAdapter = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<Flat>{
                    override fun onItemClick(demoMenu: Flat, position: Int, layoutRes: Int) {
                        appCompositionRoot.screenNavigator.createSectorFlat(demoMenu)
                    }
                },R.layout.item_house, listDataFlat,requireContext(),appTheme1){}
                binding.rvHouse.adapter = rvGenericAdapter
                binding.include.consSimmer.gone()
                binding.include.shimmer.stopShimmer()
                binding.consTool.visible()
                binding.rvHouse.visible()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}