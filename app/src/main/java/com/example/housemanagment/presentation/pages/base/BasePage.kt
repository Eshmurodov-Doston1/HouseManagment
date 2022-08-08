package com.example.housemanagment.presentation.pages.base

import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import com.dolatkia.animatedThemeManager.ThemeManager
import com.example.housemanagment.R
import com.example.housemanagment.databinding.*
import com.example.housemanagment.models.demoMenu.DemoItem
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.models.demoMenu.cash.CashDataChild
import com.example.housemanagment.models.demoMenu.cash.CashItem
import com.example.housemanagment.models.demoMenu.cash.CashItemData
import com.example.housemanagment.models.demoMenu.chat.ChatData
import com.example.housemanagment.models.demoMenu.employe.Employee
import com.example.housemanagment.models.demoMenu.flat.Flat
import com.example.housemanagment.models.demoMenu.flat.FlatData
import com.example.housemanagment.models.demoMenu.place.PlaceData
import com.example.housemanagment.presentation.activitys.AuthActivity
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.uiTheme.themes.DarkTheme
import com.example.housemanagment.uiTheme.themes.LightTheme
import com.example.housemanagment.utils.AppCompositionRoot
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.THREE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
                                          import com.example.housemanagment.utils.sharedPreference.MySharedPreferences
import com.example.housemanagment.vm.buildings.BuildingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
@AndroidEntryPoint
open class BasePage(
    @LayoutRes private val layoutRes:Int,
    ):ThemeFragment(),CoroutineScope {
    var bindingView:ViewBinding?=null
    val appCompositionRootAuth get() = (activity as AuthActivity).appCompositionRoot
    val appCompositionRoot get() = (activity as MainActivity).appCompositionRoot

    override fun syncTheme(appTheme: AppTheme) {
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        when(layoutRes){
            R.layout.fragment_document->{
                val binding = bindingView as FragmentDocumentBinding
                binding.apply {
                    include.consSimmer.setBackgroundColor(appTheme1.backgroundColorApp(requireContext()))
                    backIcon.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                    searchIcon.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                    consToolbar.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                    back.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                    toolbarText.setTextColor(appTheme1.textColorApp(requireContext()))
                    binding.searchButton.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                    val gradientDrawable = binding.searchLinear.background as GradientDrawable
                    gradientDrawable.setColor(appTheme1.backgroundColorTool(requireContext()))
                    binding.search.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                    binding.clearText.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                    binding.search.setTextColor(appTheme1.textColorApp(requireContext()))
                    binding.search.setHintTextColor(appTheme1.hintColor(requireContext()))
                }
            }

            R.layout.fragment_sector_flat->{
                val binding = bindingView as FragmentSectorFlatBinding
                binding.apply {
                    shimmerInclude.consShimmer.setBackgroundColor(appTheme1.backgroundColorApp(requireContext()))
                    consToolbar.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                    backIcon.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                    toolbarText.setTextColor(appTheme1.textColorApp(requireContext()))
                    back.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                }
            }

            R.layout.fragment_company->{
                var binding = bindingView as FragmentCompanyBinding
                binding.consTool.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                binding.back.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                binding.searchButton.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                val gradientDrawable = binding.searchLinear.background as GradientDrawable
                gradientDrawable.setColor(appTheme1.backgroundColorTool(requireContext()))
                binding.search.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                binding.textToolbar.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.iconSearch.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                binding.backIcon.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                binding.clearText.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                binding.search.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.search.setHintTextColor(appTheme1.hintColor(requireContext()))
            }
            R.layout.fragment_houses->{
                var binding = bindingView as FragmentHousesBinding
                binding.consTool.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                binding.back.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                binding.searchButton.setCardBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                val gradientDrawable = binding.searchLinear.background as GradientDrawable
                gradientDrawable.setColor(appTheme1.backgroundColorTool(requireContext()))
                binding.search.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                binding.textToolbar.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.iconSearch.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                binding.backIcon.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                binding.clearText.setColorFilter(appTheme1.iconColor(requireContext()), PorterDuff.Mode.SRC_ATOP)
                binding.search.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.search.setHintTextColor(appTheme1.hintColor(requireContext()))
            }
            R.layout.fragment_main->{
                val binding = bindingView as FragmentMainBinding
                binding.viewPager2.setBackgroundColor(appTheme1.backgroundColorApp(requireContext()))
                binding.bottomNavigation.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
            }

            R.layout.fragment_auth->{
                  val binding = bindingView as FragmentAuthBinding
                  binding.consAuth.setBackgroundColor(appTheme1.backgroundColorApp(requireContext()))
                  /** Password InputText **/
                  val passWordDrawable = binding.password.background as GradientDrawable
                  passWordDrawable.setColor(appTheme1.itemButtonColor(requireContext()))
                  binding.password.setTextColor(appTheme1.textColorApp(requireContext()))
                  binding.titlePassword.setTextColor(appTheme1.hintColor(requireContext()))
                  /** Login InputText **/
                  val loginDrawable = binding.login.background as GradientDrawable
                  loginDrawable.setColor(appTheme1.itemButtonColor(requireContext()))
                  binding.login.setTextColor(appTheme1.textColorApp(requireContext()))
                  binding.title.setTextColor(appTheme1.hintColor(requireContext()))
              }

            R.layout.fragment_settings->{
                  val binding = bindingView as FragmentSettingsBinding
                binding.themeCheckBox.setOnCheckedChangeListener { view, isChecked ->
                    if (isChecked){
                        ThemeManager.instance.changeTheme(DarkTheme(),view)
                        appCompositionRoot.mActivity.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
                        appCompositionRoot.mySharedPreferencesApp.theme = true
                    }else{
                        ThemeManager.instance.changeTheme(LightTheme(),view)
                        appCompositionRoot.mActivity.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        appCompositionRoot.mySharedPreferencesApp.theme = false
                    }
                }
                appCompositionRoot.colorStatusAndNavigationBars(appTheme1.backgroundColorTool(requireContext()))
                binding.consSettings.setBackgroundColor(appTheme1.backgroundColorApp(requireContext()))
                binding.consToolbar.setBackgroundColor(appTheme1.backgroundColorTool(requireContext()))
                binding.toolbarText.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.modeTv.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.lanTv.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.langTex.setTextColor(appTheme1.textColorApp(requireContext()))
                binding.line.setBackgroundColor(appTheme1.lineColor(requireContext()))
                val shapeDrawable = binding.consLogout.background as GradientDrawable
                shapeDrawable.setColor(appTheme1.itemButtonColor(requireContext()))
            }

        }
    }

    fun getDemoMenu():ArrayList<DemoMenu>{
        var listDemoMenu = ArrayList<DemoMenu>()
        listDemoMenu.add(DemoMenu(context?.getString(R.string.all_home).toString(),R.drawable.ic_home,ContextCompat.getColor(requireContext(),R.color.main_card_first_color),ZERO))
        listDemoMenu.add(DemoMenu(context?.getString(R.string.sold_out_flat).toString(),R.drawable.ic_group_3,ContextCompat.getColor(requireContext(),R.color.main_card_second_color), ONE))
        listDemoMenu.add(DemoMenu(context?.getString(R.string.contracts).toString(),R.drawable.ic_four,ContextCompat.getColor(requireContext(),R.color.main_card_four_color), TWO))
        listDemoMenu.add(DemoMenu(context?.getString(R.string.valuta).toString(),R.drawable.ic_currency,ContextCompat.getColor(requireContext(),R.color.image_empty_color), THREE))
        return listDemoMenu
    }


    fun getEmployee():ArrayList<Employee>{
        var listEmployee = ArrayList<Employee>()
        listEmployee.add(Employee("Dostonbek Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        listEmployee.add(Employee("Murodjon Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        listEmployee.add(Employee("Marjona Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        listEmployee.add(Employee("Anvar Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        listEmployee.add(Employee("Dilorom Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        listEmployee.add(Employee("Dilshod Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        listEmployee.add(Employee("Raxmatullo Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        listEmployee.add(Employee("G'ani Eshmurodov","Полный доступ","04.07.2022","+998994206278"))
        return listEmployee
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate + Job()


}
