package com.example.housemanagment.presentation.pages.flatData

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.databinding.FragmentFlatDataBinding
import com.example.housemanagment.models.demoMenu.flat.Flat
import com.example.housemanagment.models.house.House
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.*
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.format
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.vm.AuthViewModel
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import java.math.BigInteger


private const val ARG_PARAM1 = "house"
@AndroidEntryPoint
class FlatDataFragment : BasePage(R.layout.fragment_flat_data) {
    private var house: House? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            house = it.getSerializable(ARG_PARAM1) as House
        }
    }
    private var _binding:FragmentFlatDataBinding?=null
    private val binding get() = _binding!!
    private val authViewModel:AuthViewModel by viewModels()
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

            collapsing.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
            collapsing.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

            appbar.addOnOffsetChangedListener(object: InfoClass(){
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                    when(state){
                        State.COLLAPSED->{
                            if (authViewModel.sharedPreferences.theme == true){
                                toolbar.setBackgroundColor(requireActivity().getColor(R.color.backgroundDarkColor_Tool))
                            }else{
                                toolbar.setBackgroundColor(requireActivity().getColor(R.color.backgroundColor))
                            }
                        }
                        State.EXPANDED->{
                            toolbar.setBackgroundColor(Color.TRANSPARENT)
                        }
                        else -> {}
                    }
                }
            })

            //appCompositionRoot.setImage(imageFlat, house?.image.toString(),0F)

            collapsing.title = "${appCompositionRoot.mActivity.getString(R.string.number)} ${house?.number.toString()}"
            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }


            val header = DataTableHeader.Builder()
                .item(appCompositionRoot.mContext.getString(R.string.date), 1)
                .item(appCompositionRoot.mContext.getString(R.string.payment), 1)
                .item(appCompositionRoot.mContext.getString(R.string.paid), 1)
                .build()
            val rows = ArrayList<DataTableRow>()


            var all_sum:BigInteger = BigInteger.ZERO
            var home_sum:BigInteger = BigInteger.ZERO



            for (i in 1..30) {
                val row = DataTableRow.Builder()
                    .value("$i.07.2022")
                    .value((i * 1000000).toString() + " so'm")
                    .value((i * 100000).toString() + " so'm")
                    .build()

                all_sum += BigInteger((i * 100000).toString())
                home_sum += BigInteger((i * 1000000).toString())
                rows.add(row)
            }

           if (authViewModel.sharedPreferences.theme == true){
               viewTableColor(R.color.textDarkColor,R.color.backgroundDarkColor)
               back.setCardBackgroundColor(requireActivity().getColor(R.color.backgroundDarkColor_Tool))
               backIcon.setColorFilter(requireActivity().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
               collapsing.setCollapsedTitleTextColor(requireActivity().getColor(R.color.textDarkColor))
               collapsing.setContentScrimColor(requireActivity().getColor(R.color.backgroundDarkColor_Tool))
           }else{
               viewTableColor(R.color.textColor,R.color.backgroundColor)
               back.setCardBackgroundColor(requireActivity().getColor(R.color.backgroundColor))
               backIcon.setColorFilter(requireActivity().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
               collapsing.setCollapsedTitleTextColor(requireActivity().getColor(R.color.textColor))
               collapsing.setContentScrimColor(requireActivity().getColor(R.color.backgroundColor))
           }

            dataTable.headerTextSize = 15F
            dataTable.rowTextSize = 12F
            dataTable.typeface = Typeface.DEFAULT
            dataTable.header = header
            dataTable.rows = rows
            dataTable.inflate(appCompositionRoot.mContext)
            paidText.textApp(all_sum.toDouble().format())
            homeSum.textApp(home_sum.toDouble().format())
            dataTable.shadow = 0f
            var data = all_sum.minus(home_sum)
            if (data.toLong()<ZERO){
                debtText.textApp(data.toDouble().format())
            }else{
                debtText.textApp("_")
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
        }
    }

}