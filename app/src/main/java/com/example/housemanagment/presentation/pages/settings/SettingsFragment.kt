package com.example.housemanagment.presentation.pages.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.housemanagment.R
import com.example.housemanagment.databinding.FragmentSettingsBinding
import com.example.housemanagment.presentation.activitys.AuthActivity
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.fetchResult
import com.example.housemanagment.utils.extension.startNewActivity
import com.example.housemanagment.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : BasePage(R.layout.fragment_settings) {
    private var _binding:FragmentSettingsBinding?= null
    private val binding get() = _binding!!
    private val appCompositionRoot get() = (activity as MainActivity).appCompositionRoot
    private val authViewModel:AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        bindingView = binding
        appCompositionRootBase = appCompositionRoot
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            themeCheckBox.isChecked = authViewModel.sharedPreferences.theme ?:false
            cardLogout.setOnClickListener {
                appCompositionRoot.otherDialog(ZERO){
                    if (it){
                        authViewModel.logOut()
                        launch {
                            authViewModel.logOut.fetchResult(appCompositionRoot.uiControllerApp){ logOutRes->
                                appCompositionRoot.customToast(logOutRes?.message.toString())
                                authViewModel.sharedPreferences.clear()
                                launch { authViewModel.deleteUserTable() }
                                appCompositionRoot.mActivity.startNewActivity(AuthActivity::class.java)
                                appCompositionRoot.mActivity.finish()
                            }
                        }
                    }
                }
            }
        }
    }
}