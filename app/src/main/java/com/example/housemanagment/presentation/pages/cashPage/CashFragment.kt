package com.example.housemanagment.presentation.pages.cashPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentCashBinding
import com.example.housemanagment.models.demoMenu.cash.CashItem
import com.example.housemanagment.presentation.pages.base.BasePage

class CashFragment : BasePage(R.layout.fragment_cash) {
    private var _binding:FragmentCashBinding?=null
    private val binding get() = _binding!!
    private lateinit var  rvGenericAdapterCashItem:RvGenericAdapter<CashItem>
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentCashBinding.inflate(inflater,container,false)
          bindingView = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }

    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        rvGenericAdapterCashItem = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<CashItem>{
            override fun onItemClick(demoMenu: CashItem, position: Int, layoutRes: Int) {

            }
        },R.layout.item_cash,getCash(),appCompositionRoot.mContext,appTheme1){t->}
        binding.rvCash.adapter = ConcatAdapter(rvGenericAdapterCashItem)
        binding.consTool.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
        binding.textView.setTextColor(appTheme1.textColorApp(requireContext()))
        binding.filter.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
    }
}