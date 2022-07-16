package com.example.housemanagment.presentation.pages.base

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeFragment
import com.dolatkia.animatedThemeManager.ThemeManager
import com.example.housemanagment.R
import com.example.housemanagment.databinding.FragmentAuthBinding
import com.example.housemanagment.databinding.FragmentMainBinding
import com.example.housemanagment.databinding.FragmentSettingsBinding
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
import com.example.housemanagment.uiTheme.themes.DarkTheme
import com.example.housemanagment.uiTheme.themes.LightTheme
import com.example.housemanagment.utils.AppCompositionRoot
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.THREE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
                                          import com.example.housemanagment.utils.sharedPreference.MySharedPreferences
                                          import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
open class BasePage(
    @LayoutRes private val layoutRes:Int,
    ):ThemeFragment(),CoroutineScope {
    var bindingView:ViewBinding?=null
    var appCompositionRootBase: AppCompositionRoot?=null
    override fun syncTheme(appTheme: AppTheme) {
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        when(layoutRes){

            R.layout.fragment_main->{
                val binding = bindingView as FragmentMainBinding
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
                        appCompositionRootBase?.mActivity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
                        appCompositionRootBase?.mySharedPreferencesApp?.theme = true
                    }else{
                        ThemeManager.instance.changeTheme(LightTheme(),view)
                        appCompositionRootBase?.mActivity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        appCompositionRootBase?.mySharedPreferencesApp?.theme = false
                    }
                }
                appCompositionRootBase?.colorStatusAndNavigationBars(appTheme1.backgroundColorTool(requireContext()))
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
        listDemoMenu.add(DemoMenu(context?.getString(R.string.all_home).toString(),"25 заказов",R.drawable.ic_home,ContextCompat.getColor(requireContext(),R.color.main_card_first_color),ZERO))
        listDemoMenu.add(DemoMenu(context?.getString(R.string.sold_out_flat).toString(),"54 035 руб",R.drawable.ic_group_3,ContextCompat.getColor(requireContext(),R.color.main_card_second_color), ONE))
        listDemoMenu.add(DemoMenu(context?.getString(R.string.get_summ).toString(),"345 000 руб",R.drawable.ic_three_con,ContextCompat.getColor(requireContext(),R.color.main_card_three_color), TWO))
        listDemoMenu.add(DemoMenu(context?.getString(R.string.return_summ).toString(),"345 000 руб",R.drawable.ic_four,ContextCompat.getColor(requireContext(),R.color.main_card_four_color), THREE))
        return listDemoMenu
    }

    fun getDataItem():ArrayList<DemoItem>{
        var listDemoItem = ArrayList<DemoItem>()
        listDemoItem.add(DemoItem("Caegory 1",121,312491658.0))
        listDemoItem.add(DemoItem("Caegory 2",144,512123121.0))
        listDemoItem.add(DemoItem("Caegory 3",33,41491194.0))
        listDemoItem.add(DemoItem("Caegory 4 \uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02",140,312491658.0))
        listDemoItem.add(DemoItem("Caegory 5",121,312491658.0))
        listDemoItem.add(DemoItem("Caegory 6",144,512123121.0))
        listDemoItem.add(DemoItem("Caegory 7",33,41491194.0))
        listDemoItem.add(DemoItem("Caegory 8 \uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02",140,312491658.0))
        listDemoItem.add(DemoItem("Caegory 9",121,312491658.0))
        listDemoItem.add(DemoItem("Caegory 10",144,512123121.0))
        listDemoItem.add(DemoItem("Caegory 11",33,41491194.0))
        listDemoItem.add(DemoItem("Caegory 12 \uD83D\uDE02\uD83D\uDE02\uD83D\uDE02\uD83D\uDE02",140,312491658.0))
        return listDemoItem
    }


    fun getCash():ArrayList<CashItem>{
        var listCash = ArrayList<CashItem>()
            // naqd Pul
        listCash.add(CashItem(12000000.0,"12.01.2022","Dostonbek Eshmurodov",0))
        // Carta orqali
        listCash.add(CashItem(12000000.0,"12.05.2022","Dostonbek Eshmurodov",1))
        // Birlashgan to'lov
        listCash.add(CashItem(12000000.0,"12.05.2022","Dostonbek Eshmurodov",2))
        // naqd Pul
        listCash.add(CashItem(12000000.0,"12.01.2022","Dostonbek Eshmurodov",0))
        // Carta orqali
        listCash.add(CashItem(12000000.0,"12.05.2022","Dostonbek Eshmurodov",1))
        // Birlashgan to'lov
        listCash.add(CashItem(12000000.0,"12.05.2022","Dostonbek Eshmurodov",2))
        return listCash
    }

    fun loadCash():ArrayList<CashDataChild>{
        var listCashData = ArrayList<CashDataChild>()
        var listCash = ArrayList<CashItemData>()
        listCash.add(CashItemData(R.drawable.ic_cash_summ,context?.getString(R.string.cash).toString(),500000.0))
        listCash.add(CashItemData(R.drawable.ic_card,context?.getString(R.string.card).toString(),250000.0))
        listCashData.add(CashDataChild("Глобальные кассы",listCash))
        listCash = ArrayList()
        listCash.add(CashItemData(R.drawable.ic_cash_summ,context?.getString(R.string.cash).toString(),500000.0))
        listCash.add(CashItemData(R.drawable.ic_card,context?.getString(R.string.card).toString(),250000.0))
        listCash.add(CashItemData(R.drawable.ic_cash_summ,context?.getString(R.string.cash).toString(),500000.0))
        listCash.add(CashItemData(R.drawable.ic_card,context?.getString(R.string.card).toString(),250000.0))
        listCashData.add(CashDataChild("Оболонь",listCash))
        listCash = ArrayList()
        listCash.add(CashItemData(R.drawable.ic_cash_summ,context?.getString(R.string.cash).toString(),500000.0))
        listCash.add(CashItemData(R.drawable.ic_card,context?.getString(R.string.card).toString(),250000.0))
        listCashData.add(CashDataChild("Глобальные кассы",listCash))
        listCash = ArrayList()
        listCash.add(CashItemData(R.drawable.ic_cash_summ,context?.getString(R.string.cash).toString(),500000.0))
        listCash.add(CashItemData(R.drawable.ic_card,context?.getString(R.string.card).toString(),250000.0))
        listCash.add(CashItemData(R.drawable.ic_cash_summ,context?.getString(R.string.cash).toString(),500000.0))
        listCash.add(CashItemData(R.drawable.ic_card,context?.getString(R.string.card).toString(),250000.0))
        listCashData.add(CashDataChild("Оболонь",listCash))
        return listCashData
    }


    fun getPlaceData():ArrayList<PlaceData>{
        var listPlace = ArrayList<PlaceData>()
        listPlace.add(PlaceData("Nest One",121,312491658.0))
        listPlace.add(PlaceData("Yangi choshtepa",121,312491658.0))
        listPlace.add(PlaceData("Toshkent City",121,312491658.0))
        listPlace.add(PlaceData("Qorasuv",121,312491658.0))
        listPlace.add(PlaceData("Nest One",121,312491658.0))
        listPlace.add(PlaceData("Yangi choshtepa",121,312491658.0))
        listPlace.add(PlaceData("Toshkent City",121,312491658.0))
        listPlace.add(PlaceData("Qorasuv",121,312491658.0))
        listPlace.add(PlaceData("Nest One",121,312491658.0))
        listPlace.add(PlaceData("Yangi choshtepa",121,312491658.0))
        listPlace.add(PlaceData("Toshkent City",121,312491658.0))
        listPlace.add(PlaceData("Qorasuv",121,312491658.0))
        listPlace.add(PlaceData("Nest One",121,312491658.0))
        listPlace.add(PlaceData("Yangi choshtepa",121,312491658.0))
        listPlace.add(PlaceData("Toshkent City",121,312491658.0))
        listPlace.add(PlaceData("Qorasuv",121,312491658.0))
        return listPlace
    }

    fun getCompany():ArrayList<PlaceData>{
        var listPlace = ArrayList<PlaceData>()
        listPlace.add(PlaceData("Block A",6,312491658.0))
        listPlace.add(PlaceData("Block B",7,312491658.0))
        listPlace.add(PlaceData("Block C",4,312491658.0))
        listPlace.add(PlaceData("Block D",3,312491658.0))
        listPlace.add(PlaceData("Block E",7,312491658.0))
        listPlace.add(PlaceData("Block G",7,312491658.0))
        listPlace.add(PlaceData("Block F",9,312491658.0))
        listPlace.add(PlaceData("Block R",2,312491658.0))
        listPlace.add(PlaceData("Block A",6,312491658.0))
        listPlace.add(PlaceData("Block B",7,312491658.0))
        listPlace.add(PlaceData("Block C",4,312491658.0))
        listPlace.add(PlaceData("Block D",3,312491658.0))
        listPlace.add(PlaceData("Block E",7,312491658.0))
        listPlace.add(PlaceData("Block G",7,312491658.0))
        listPlace.add(PlaceData("Block F",9,312491658.0))
        listPlace.add(PlaceData("Block R",2,312491658.0))
        return listPlace
    }



    fun getAllflatData():ArrayList<FlatData>{
        var flatData = ArrayList<FlatData>()
        var flatList = ArrayList<Flat>()
        flatList.add(Flat(1,"https://images.unsplash.com/photo-1579963824000-7d7b70b2f7a3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8ZmxhdHN8ZW58MHx8MHx8&w=1000&q=80","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №1","В рассрочку","36 Месяцев","31 Месяц",123831591.0,3831591.0,312491658.0,431414951.0,0,62.0))
        flatList.add(Flat(2,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg",null,"Африка ко’часи," +
                "Подъезд 2, №2",null,null,null,null,null,null,431414951.0,1,43.0))
        flatList.add(Flat(3,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №3","В рассрочку","36 Месяцев","31 Месяц",123831591.0,3831591.0,312491658.0,431414951.0,2,52.0))
        flatData.add(FlatData("C1",flatList))
        flatList = ArrayList()
        flatList.add(Flat(1,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №1","В рассрочку","36 Месяцев","31 Месяц",123831591.0,3831591.0,312491658.0,431414951.0,0,32.0))
        flatList.add(Flat(2,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №2",null,null,null,null,null,null,431414951.0,1,62.0))
        flatList.add(Flat(3,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №3","В рассрочку","36 Месяцев","31 Месяц",123831591.0,3831591.0,312491658.0,431414951.0,2,32.0))
        flatData.add(FlatData("C2",flatList))
        flatList = ArrayList()
        flatList.add(Flat(1,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №1","В рассрочку","36 Месяцев","31 Месяц",123831591.0,3831591.0,312491658.0,431414951.0,0,62.0))
        flatList.add(Flat(2,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №2",null,null,null,null,null,null,431414951.0,1,62.0))
        flatList.add(Flat(3,"https://image.shutterstock.com/image-photo/modern-apartment-buildings-richmond-british-260nw-501606547.jpg","Dostonbek Eshmurodov","Африка ко’часи," +
                "Подъезд 2, №3","В рассрочку","36 Месяцев","31 Месяц",123831591.0,3831591.0,312491658.0,431414951.0,2,62.0))
        flatData.add(FlatData("C3",flatList))
        return flatData
    }

    fun getChatData():List<ChatData>{
        var listChatData = ArrayList<ChatData>()
        listChatData.add(ChatData("Salom yaxshimisiz",1))
        listChatData.add(ChatData("Voaleykum assalom nima muammo?",0))
        listChatData.add(ChatData("Tinch prosta applicationni ishlatish uchun yozvomman",1))
        listChatData.add(ChatData("Ha yaxshi.",0))
        listChatData.add(ChatData("Salom yaxshimisiz",1))
        listChatData.add(ChatData("Voaleykum assalom nima muammo?",0))
        listChatData.add(ChatData("Tinch prosta applicationni ishlatish uchun yozvomman",1))
        listChatData.add(ChatData("Ha yaxshi.",0))
        listChatData.add(ChatData("Salom yaxshimisiz",1))
        listChatData.add(ChatData("Voaleykum assalom nima muammo?",0))
        listChatData.add(ChatData("Tinch prosta applicationni ishlatish uchun yozvomman",1))
        listChatData.add(ChatData("Ha yaxshi.",0))
        listChatData.add(ChatData("Salom yaxshimisiz",1))
        listChatData.add(ChatData("Voaleykum assalom nima muammo?",0))
        listChatData.add(ChatData("Tinch prosta applicationni ishlatish uchun yozvomman",1))
        listChatData.add(ChatData("Ha yaxshi.",0))
        listChatData.add(ChatData("Salom yaxshimisiz",1))
        listChatData.add(ChatData("Voaleykum assalom nima muammo?",0))
        listChatData.add(ChatData("Tinch prosta applicationni ishlatish uchun yozvomman",1))
        listChatData.add(ChatData("Ha yaxshi.",0))
        listChatData.add(ChatData("Salom yaxshimisiz",1))
        listChatData.add(ChatData("Voaleykum assalom nima muammo?",0))
        listChatData.add(ChatData("Tinch prosta applicationni ishlatish uchun yozvomman",1))
        listChatData.add(ChatData("Ha yaxshi.",0))
        return  listChatData
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
