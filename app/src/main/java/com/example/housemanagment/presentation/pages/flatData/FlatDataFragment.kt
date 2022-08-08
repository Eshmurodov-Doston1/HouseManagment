package com.example.housemanagment.presentation.pages.flatData

import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.databinding.FragmentFlatDataBinding
import com.example.housemanagment.models.flatData.Home
import com.example.housemanagment.models.house.House
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.USD
import com.example.housemanagment.utils.AppConstant.UZB
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.*
import com.example.housemanagment.vm.AuthViewModel
import com.example.housemanagment.vm.buildings.BuildingViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import kotlinx.coroutines.launch


private const val ARG_PARAM1 = "house"
@AndroidEntryPoint
class FlatDataFragment : BasePage(R.layout.fragment_flat_data) {
    private var house: String? = null
    private val buildingViewModel:BuildingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            house = it.getString(ARG_PARAM1)
        }
    }
    private var _binding:FragmentFlatDataBinding?=null
    private val binding get() = _binding!!
    private val authViewModel:AuthViewModel by viewModels()
    private lateinit var listHomeData:ArrayList<Home>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlatDataBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            var houseData = Gson().fromJson(house,House::class.java)
            listHomeData = ArrayList()
            textToolbar.textApp("${appCompositionRoot.mActivity.getString(R.string.number)} ${houseData?.number.toString()}")
            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }


            val header = DataTableHeader.Builder()
                .item(appCompositionRoot.mContext.getString(R.string.date), 1)
                .item(appCompositionRoot.mContext.getString(R.string.payment), 1)
                .item(appCompositionRoot.mContext.getString(R.string.paid), 1)
                .build()
            val rows = ArrayList<DataTableRow>()


            var all_sum = 0.0
            var home_sum = 0.0



            buildingViewModel.getHomeData(houseData?.id?:0)

            launch {
                buildingViewModel.homeData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                    binding.includeApp.lottie.gone()
                    binding.cardSum.visible()

                    result?.list?.onEach { home->
                        if (result.currency == UZB){
                            val row = DataTableRow.Builder()
                                .value(home.month_id)
                                .value(home.pending.toDouble().format() +" "+ requireActivity().getString(R.string.money_type))
                                .value(home.paid.toDouble().format() +" "+ requireActivity().getString(R.string.money_type))
                                .build()
                            rows.add(row)
                        }else if (result.currency == USD){
                            val row = DataTableRow.Builder()
                                .value(home.month_id)
                                .value(home.pending.toDouble().format() +" "+ requireActivity().getString(R.string.money_type_us))
                                .value(home.paid.toDouble().format() +" "+ requireActivity().getString(R.string.money_type_us))
                                .build()
                            rows.add(row)
                        }

                        all_sum += home.pending.toDouble()
                        home_sum += home.paid.toDouble()
                    }

                    dataTable.headerTextSize = 15F
                    dataTable.rowTextSize = 12F
                    dataTable.typeface = Typeface.DEFAULT
                    dataTable.header = header
                    dataTable.rows = rows
                    dataTable.inflate(appCompositionRoot.mContext)
                    paidText.textApp(all_sum.format())
                    homeSum.textApp(home_sum.format())
                    dataTable.shadow = 0f
                    var data = home_sum.minus(all_sum)

                    if (data.toLong()<ZERO){
                        debtText.textApp(data.format())
                    }else{
                        debtText.textApp("_")
                    }
                }
            }


           if (authViewModel.sharedPreferences.theme == true){
               viewTableColor(R.color.textDarkColor,R.color.backgroundDarkColor)
               back.setCardBackgroundColor(requireActivity().getColor(R.color.backgroundDarkColor_Tool))
               backIcon.setColorFilter(requireActivity().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
           }else{
               viewTableColor(R.color.textColor,R.color.backgroundColor)
               back.setCardBackgroundColor(requireActivity().getColor(R.color.backgroundColor))
               backIcon.setColorFilter(requireActivity().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP)
           }


        }
    }

    fun viewTableColor(textColor:Int,backGroundColor:Int){
        binding.apply {
            dataTable.rowBackgroundColor = requireActivity().getColor(backGroundColor)
            dataTable.headerBackgroundColor =requireActivity().getColor(backGroundColor)
            dataTable.headerTextColor = requireActivity().getColor(textColor)
            dataTable.rowTextColor = requireActivity().getColor(textColor)
            allSum.setTextColor(requireActivity().getColor(textColor))
            allFlat.setTextColor(requireActivity().getColor(textColor))
            allDebt.setTextColor(requireActivity().getColor(textColor))
            paidText.setTextColor(requireActivity().getColor(textColor))
            homeSum.setTextColor(requireActivity().getColor(textColor))
            debtText.setTextColor(requireActivity().getColor(textColor))
            cardSum.setCardBackgroundColor(requireActivity().getColor(backGroundColor))
            textToolbar.setTextColor(requireActivity().getColor(textColor))
        }
    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        binding.consTool.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
    }

}