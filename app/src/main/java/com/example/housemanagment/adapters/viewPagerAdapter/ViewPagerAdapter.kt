package com.example.housemanagment.adapters.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.housemanagment.presentation.pages.cashPage.CashFragment
import com.example.housemanagment.presentation.pages.employees.EmployeesFragment
import com.example.housemanagment.presentation.pages.reportPage.ReportFragment
import com.example.housemanagment.presentation.pages.settings.SettingsFragment
import com.example.housemanagment.utils.AppConstant.FOUR
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.THREE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return FOUR
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            ZERO->{
                ReportFragment()
            }
            ONE->{
                CashFragment()
            }
            TWO->{
                EmployeesFragment()
            }
            THREE->{
                SettingsFragment()
            }
            else->{
                ReportFragment()
            }
        }
    }
}