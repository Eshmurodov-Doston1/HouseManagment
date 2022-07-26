package com.example.housemanagment.presentation.pages.employees

import android.Manifest
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.RvGenericAdapter
import com.example.housemanagment.databinding.FragmentEmployeesBinding
import com.example.housemanagment.models.demoMenu.employe.Employee
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.isNotNullOrEmpty
import com.example.housemanagment.utils.extension.visible
import com.permissionx.guolindev.PermissionX


class EmployeesFragment : BasePage(R.layout.fragment_employees) {
    private var _binding:FragmentEmployeesBinding?=null
    private val binding get() = _binding!!
    private lateinit var rvGenericAdapter:RvGenericAdapter<Employee>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            includeShimmer.shimmer.startShimmer()
            Handler(Looper.getMainLooper()).postDelayed({
                includeApp.lottie.visible()
                includeShimmer.consShimmer.gone()
            },2000)



            searchButton.setOnClickListener {
                searchLinear.visible()
                searchButton.gone()
                toolbarText.gone()
                appCompositionRoot.inputTextCreateKeyboard(search)
            }

            clearText.setOnClickListener {
                if (search.text.toString().isNotNullOrEmpty()){
                    search.text.clear()
                }else{
                   searchButton.visible()
                    toolbarText.visible()
                    searchLinear.gone()
                    appCompositionRoot.hideKeyboard(search)
                }
            }

            searchEmploye()
        }
    }


    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme

        rvGenericAdapter = RvGenericAdapter(object:RvGenericAdapter.OnItemClickListener<Employee>{
            override fun onItemClick(demoMenu: Employee, position: Int, layoutRes: Int) {

            }
        },R.layout.employe_item,getEmployee(),appCompositionRoot.mContext,appTheme1){t->
            try {
                PermissionX.init(activity)
                    .permissions(Manifest.permission.CALL_PHONE)
                    .onExplainRequestReason { scope, deniedList ->
                        scope.showRequestReasonDialog(deniedList, appCompositionRoot.mContext.getString(R.string.permission_str), "OK", appCompositionRoot.mContext.getString(R.string.cancel))
                    }
                    .request { allGranted, grantedList, deniedList ->
                        val employee = t as Employee
                        if (allGranted) {
                            appCompositionRoot.callAdmin(employee.phoneNumber)
                        } else {
                            appCompositionRoot.createSettings()
                        }
                    }
            }catch (e:Exception){
                appCompositionRoot.errorDialog(e.message.toString(),-1){}
            }

        }
        binding.rvEmployees.adapter = rvGenericAdapter
        binding.consToolbar.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
        binding.toolbarText.setTextColor(appTheme1.textColorApp(requireContext()))
        binding.searchButton.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
        binding.searchImage.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
        val gradientDrawable = binding.searchLinear.background as GradientDrawable
        gradientDrawable.setColor(appTheme1.backgroundColorTool(requireContext()))
        binding.search.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
        binding.clearText.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
        binding.search.setTextColor(appTheme1.textColorApp(requireContext()))
        binding.search.setHintTextColor(appTheme1.hintColor(requireContext()))
    }

    private fun searchEmploye() {
       binding.apply {
           search.addTextChangedListener {
               filterEmploye(it.toString().trim())
           }
       }
    }

    private fun filterEmploye(text: String) {
        var listEmployee = ArrayList<Employee>()
        if (text.isNotNullOrEmpty()){
            getEmployee().onEach { employee ->
                if (employee.name.lowercase().contains(text.lowercase())){
                    listEmployee.add(employee)
                }
            }
        }
        if (text.isNotNullOrEmpty()){
            rvGenericAdapter.filterData(listEmployee)
        }else{
            rvGenericAdapter.filterData(getEmployee())
        }
    }

}