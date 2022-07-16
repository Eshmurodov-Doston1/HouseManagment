package com.example.housemanagment.presentation.pages.comPany

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentCompanyBinding
import com.example.housemanagment.models.demoMenu.place.PlaceData
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.isNotNullOrEmpty
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.utils.extension.visible
private const val ARG_PARAM1 = "placeData"

class CompanyFragment : BasePage(R.layout.fragment_company) {
    private var placeData: PlaceData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeData = it.getSerializable(ARG_PARAM1) as PlaceData
        }
    }
    private var _binding:FragmentCompanyBinding?=null
    private val binding get() = _binding!!
    private lateinit var genericAdapterPlace: RvGenericAdapter<PlaceData>
    private val appCompositionRoot get() = (activity as MainActivity).appCompositionRoot
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            include.shimmer.startShimmer()
            Handler(Looper.getMainLooper()).postDelayed({
                include.shimmer.stopShimmer()
                consTool.visible()
                rvHouse.visible()
                include.consSimmer.gone()
            },2000)



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





            textToolbar.textApp(placeData?.name.toString())
            genericAdapterPlace = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<PlaceData>{
                override fun onItemClick(demoMenu: PlaceData, position: Int, layoutRes: Int) {
                   appCompositionRoot.screenNavigator.createFlatScreen(demoMenu)
                }
            },R.layout.item_place,getCompany(),appCompositionRoot.mContext){t->}
            rvHouse.adapter = genericAdapterPlace
            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }
            searchCompany()
        }

    }

    private fun searchCompany() {
        binding.apply {
            search.addTextChangedListener {
                filterEmploye(it.toString().trim())
            }
        }
    }

    private fun filterEmploye(text: String) {
        var listPlaceData = ArrayList<PlaceData>()
        if (text.isNotNullOrEmpty()){
            getCompany().onEach { employee ->
                if (employee.name.lowercase().contains(text.lowercase())){
                    listPlaceData.add(employee)
                }
            }
        }
        if (text.isNotNullOrEmpty()){
            genericAdapterPlace.filterData(listPlaceData)
        }else{
            genericAdapterPlace.filterData(getCompany())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}