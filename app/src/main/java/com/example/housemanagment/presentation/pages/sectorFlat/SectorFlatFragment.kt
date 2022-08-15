package com.example.housemanagment.presentation.pages.sectorFlat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.adapterFlat.ChildAdapterFlat
import com.example.housemanagment.databinding.FragmentSectorFlatBinding
import com.example.housemanagment.models.bookingData.Booking
import com.example.housemanagment.models.flat.Flat
import com.example.housemanagment.models.house.House
import com.example.housemanagment.models.price.SendPrice
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.fetchResult
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.utils.extension.visible
import com.example.housemanagment.vm.buildings.BuildingViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


private const val ARG_PARAM1 = "flat"

class SectorFlatFragment : BasePage(R.layout.fragment_sector_flat) {
    private var flat: Flat? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flat = it.getSerializable(ARG_PARAM1) as Flat
        }
    }
    private var _binidng:FragmentSectorFlatBinding?=null
    private val binding get() = _binidng!!
    private lateinit var childAdapterFlat: ChildAdapterFlat
    var gson:Gson = Gson()
    private val buildingViewModel:BuildingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binidng = FragmentSectorFlatBinding.inflate(inflater,container,false)
        bindingView = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            shimmerInclude.shimmer.startShimmer()
            Handler(Looper.getMainLooper()).postDelayed({
                shimmerInclude.shimmer.stopShimmer()
                shimmerInclude.consShimmer.gone()
                consToolbar.visible()
                rvFlat.visible()
            },2000)

            toolbarText.textApp(flat?.name.toString())

            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }
        }
    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        flatData(appTheme1)
    }

    fun flatData(appTheme: com.example.housemanagment.uiTheme.AppTheme){
        launch {
            buildingViewModel.getHouse(flat?.id?:0)
            buildingViewModel.houseData.fetchResult(appCompositionRoot.uiControllerApp){ result->

               if (result?.success?.list?.isEmpty() == true) binding.includeApp.lottie.visible()
                childAdapterFlat = ChildAdapterFlat(result?.success?.list as ArrayList<House>,object:ChildAdapterFlat.OnItemClickListener{
                    override fun onItemClick(house: House, position: Int) {
                        val toJson = gson.toJson(house)
                        appCompositionRoot.screenNavigator.createFragmentFlatData(toJson,ZERO)
                    }
                    override fun onItemClickCall(house: House, position: Int) {
                        appCompositionRoot.call("+${house.phone}")
                    }

                    override fun onItemClickListenerBooking(house: House, position: Int,isPrice:Boolean) {
                        if (isPrice){
                            appCompositionRoot.dialogSumma(appTheme){ str->
                                var sendPrice = SendPrice(str)
                                buildingViewModel.flatPrice(sendPrice,house.id)
                                launch {
                                    buildingViewModel.flatListPrice.fetchResult(appCompositionRoot.uiControllerApp){ result->
                                        Log.e("ResData", result.toString())
                                    }
                                }
                            }
                        }else{
                            appCompositionRoot.dialogComment(appTheme) { str->
                                val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
                                var booking = Booking(str,currentDate)
                                buildingViewModel.bookingSave(booking,house.id)
                                launch {
                                    buildingViewModel.bookingData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                                        flatData(appTheme)
                                    }
                                }
                            }
                        }
                    }
                },appCompositionRoot, appTheme = appTheme)
                binding.rvFlat.adapter = childAdapterFlat
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binidng  = null
    }
}