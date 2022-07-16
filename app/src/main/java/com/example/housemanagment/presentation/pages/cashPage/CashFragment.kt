package com.example.housemanagment.presentation.pages.cashPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentCashBinding
import com.example.housemanagment.models.demoMenu.cash.CashDataChild
import com.example.housemanagment.models.demoMenu.cash.CashItem
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage

class CashFragment : BasePage(R.layout.fragment_cash) {
    private var _binding:FragmentCashBinding?=null
    private val binding get() = _binding!!
    private lateinit var  rvGenericAdapterCashItem:RvGenericAdapter<CashItem>
    private val compositionRoot get() = (activity as MainActivity).appCompositionRoot
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentCashBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            rvGenericAdapterCashItem = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<CashItem>{
                override fun onItemClick(demoMenu: CashItem, position: Int, layoutRes: Int) {

                }
            },R.layout.item_cash,getCash(),compositionRoot.mContext){t->}
            rvCash.adapter = ConcatAdapter(rvGenericAdapterCashItem)
        }
    }
}