package com.example.housemanagment.presentation.activitys


import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.example.housemanagment.R
import com.example.housemanagment.databinding.ActivityAuthBinding
import com.example.housemanagment.uiTheme.themes.DarkTheme
import com.example.housemanagment.uiTheme.themes.LightTheme
import com.example.housemanagment.utils.AppCompositionRoot
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.isNotNullOrEmpty
import com.example.housemanagment.utils.extension.startNewActivity
import com.example.housemanagment.utils.uiController.UiController
import com.example.housemanagment.utils.extension.visible
import com.example.housemanagment.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ThemeActivity(),UiController {
    override fun getStartTheme(): AppTheme {
        return if (authViewModel.sharedPreferences.theme == true) DarkTheme() else LightTheme()
    }
    private var _binding:ActivityAuthBinding?=null
    private val binding get() = _binding!!
    lateinit var appCompositionRoot: AppCompositionRoot
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var navController = supportFragmentManager.findFragmentById(R.id.fragment_auth) as NavHostFragment
        appCompositionRoot = AppCompositionRoot(this,navController.navController,this,authViewModel.sharedPreferences)
        if(authViewModel.sharedPreferences.token.isNotNullOrEmpty()){
            this.startNewActivity(MainActivity::class.java)
            finish()
        }
    }

    override fun syncTheme(appTheme: AppTheme) {
        var appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        if (authViewModel.sharedPreferences.theme == true) window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
        else  window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window?.statusBarColor = appTheme1.backgroundColorApp(this)
        window?.navigationBarColor = appTheme1.backgroundColorApp(this)
        binding.includeLoading.loadingCard.setCardBackgroundColor(appTheme1.backgroundColorTool(this))
        binding.includeLoading.textLoading.setTextColor(appTheme1.textColorApp(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.fragment_auth).navigateUp()
    }

    override fun showProgress() {
        binding.includeLoading.loading.visible()
    }

    override fun hideProgress() {
        binding.includeLoading.loading.gone()
    }

    override fun  error(errorCode:Long,errorMessage:String) {
        appCompositionRoot.errorDialog(errorMessage, errorCode.toInt(),authViewModel.sharedPreferences){}
    }


}