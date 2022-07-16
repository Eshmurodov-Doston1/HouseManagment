package com.example.housemanagment.presentation.pages.flatData

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.housemanagment.R
import com.example.housemanagment.databinding.FragmentFlatDataBinding
import com.example.housemanagment.models.demoMenu.flat.Flat
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.*
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.format
import com.example.housemanagment.utils.extension.textApp
import com.google.android.material.appbar.AppBarLayout
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import java.math.BigInteger


private const val ARG_PARAM1 = "flat"
class FlatDataFragment : BasePage(R.layout.fragment_flat_data) {
    private var flat: Flat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            flat = it.getSerializable(ARG_PARAM1) as Flat
        }
    }
    private var _binding:FragmentFlatDataBinding?=null
    private val binding get() = _binding!!
    private val compositionRoot get() = (activity as MainActivity).appCompositionRoot
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

            collapsing.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
            collapsing.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

            appbar.addOnOffsetChangedListener(object: InfoClass(){
                override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                    when(state){
                        State.COLLAPSED->{
                            toolbar.setBackgroundColor(resources.getColor(R.color.backgroundColor))
                        }
                        State.EXPANDED->{
                            toolbar.setBackgroundColor(Color.TRANSPARENT)
                        }
                        else -> {}
                    }
                }
            })

            compositionRoot.setImage(imageFlat, flat?.image.toString(),0F)

            collapsing.title = "${compositionRoot.mActivity.getString(R.string.number)} ${flat?.number.toString()}"
            back.setOnClickListener {
                compositionRoot.screenNavigator.popBackStack()
            }


            val header = DataTableHeader.Builder()
                .item(compositionRoot.mContext.getString(R.string.date), 1)
                .item(compositionRoot.mContext.getString(R.string.payment), 1)
                .item(compositionRoot.mContext.getString(R.string.paid), 1)
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


            dataTable.headerTextSize = 15F
            dataTable.rowTextSize = 12F

            dataTable.typeface = Typeface.DEFAULT
            dataTable.header = header
            dataTable.rows = rows
            dataTable.inflate(compositionRoot.mContext)
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
}