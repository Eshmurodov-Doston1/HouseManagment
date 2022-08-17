package com.example.housemanagment.presentation.pages.cashPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentCashBinding
import com.example.housemanagment.models.demoMenu.cash.CashItem
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.fetchResult
import com.example.housemanagment.utils.extension.format
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.vm.buildings.BuildingViewModel
import kotlinx.coroutines.launch

class CashFragment : BasePage(R.layout.fragment_cash) {
    private var _binding:FragmentCashBinding?=null
    private val binding get() = _binding!!
    private val buildingViewModel:BuildingViewModel by viewModels()
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


        binding.apply {
            buildingViewModel.getMoneyOperation()
            cardCash.setCardBackgroundColor(appTheme1.itemCardColor(requireContext()))
            cardTv.setTextColor(appTheme1.textColorApp(requireContext()))
            cashTv.setTextColor(appTheme1.textColorApp(requireContext()))
            cashBankTv.setTextColor(appTheme1.textColorApp(requireContext()))
            cashAndCardTv.setTextColor(appTheme1.textColorApp(requireContext()))
           launch {
              buildingViewModel.moneyData.fetchResult(appCompositionRoot.uiControllerApp){ result->
                  cashTv.textApp("${requireActivity().getString(R.string.cash_t)} ${result?.success?.naqd?.format().toString()}")
                  cardTv.textApp("${requireActivity().getString(R.string.card_t)} ${result?.success?.karta?.format().toString()}")
                  cashBankTv.textApp("${requireActivity().getString(R.string.card_bank_t)} ${result?.success?.bank?.format().toString()}")
                  cashAndCardTv.textApp("${requireActivity().getString(R.string.card_and_cash_t)} ${result?.success?.summa?.format().toString()}")
              }
           }
        }

        binding.consTool.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
        binding.textView.setTextColor(appTheme1.textColorApp(requireContext()))
        binding.filter.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
    }
}