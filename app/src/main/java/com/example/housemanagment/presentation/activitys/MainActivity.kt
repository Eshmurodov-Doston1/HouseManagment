package com.example.housemanagment.presentation.activitys

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.droidnet.DroidListener
import com.droidnet.DroidNet
import com.example.housemanagment.R
import com.example.housemanagment.databinding.ActivityMainBinding
import com.example.housemanagment.uiTheme.themes.DarkTheme
import com.example.housemanagment.uiTheme.themes.LightTheme
import com.example.housemanagment.utils.AppCompositionRoot
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.visible
import com.example.housemanagment.utils.uiController.UiController
import com.example.housemanagment.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ThemeActivity(), UiController,DroidListener {

    override fun getStartTheme(): AppTheme {
        return if (authViewModel.sharedPreferences.theme == true) DarkTheme() else LightTheme()
    }

    private var _binding: ActivityMainBinding?=null
    private val binding get() = _binding!!
    private var mDroidNet: DroidNet? = null
    lateinit var appCompositionRoot: AppCompositionRoot
    private val authViewModel:AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDroidNet = DroidNet.getInstance();
        mDroidNet?.addInternetConnectivityListener(this);
        var navController = supportFragmentManager.findFragmentById(R.id.fragment_main) as NavHostFragment
        appCompositionRoot = AppCompositionRoot(this,navController.navController,this,authViewModel.sharedPreferences)
    }

    override fun syncTheme(appTheme: AppTheme) {
        var appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        if (authViewModel.sharedPreferences.theme == true) window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
        else  window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        binding.consBack.setBackgroundColor(appTheme1.backgroundColorApp(this))
        window?.statusBarColor = appTheme1.backgroundColorTool(this)
        window?.navigationBarColor = appTheme1.backgroundColorTool(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDroidNet?.removeInternetConnectivityChangeListener(this)
        _binding=null
    }

    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.fragment_main).navigateUp()
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun error(errorCode: Long, errorMessage: String) {

    }

    override fun onInternetConnectivityChanged(isConnected: Boolean) {
        if (isConnected) {
            //do Stuff with internet
         binding.noInternet.gone()
        } else {
            //no internet
            binding.noInternet.visible()
        }
    }


}