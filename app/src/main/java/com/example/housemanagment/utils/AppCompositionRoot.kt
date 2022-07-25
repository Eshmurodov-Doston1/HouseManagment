package com.example.housemanagment.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.housemanagment.R
import com.example.housemanagment.databinding.CustomToastBinding
import com.example.housemanagment.models.AppModels
import com.example.housemanagment.models.demoMenu.chat.ChatData
import com.example.housemanagment.uiTheme.AppTheme
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.utils.sharedPreference.MySharedPreferences
import com.example.housemanagment.utils.uiController.UiController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.permissionx.guolindev.PermissionX

class AppCompositionRoot(
    private val activity:AppCompatActivity,
    private val navController: NavController,
    private val uiController: UiController,
    private val mySharedPreferences: MySharedPreferences,
) {
    private val mLayoutInflater: LayoutInflater get() = activity.layoutInflater
    val mActivity: AppCompatActivity get() = activity
    private val gson = Gson()
    private val dialogHelper:DialogHelper by lazy {
        DialogHelper(mContext,mLayoutInflater,activity)
    }

    val mLifecycleOwner: LifecycleOwner get() = activity
    val mContext: Context get() = activity
    val mNavController: NavController get() = navController
    val screenNavigator:ScreenNavigator by lazy {
        ScreenNavigator(navController)
    }
    val uiControllerApp get() = uiController
    val mySharedPreferencesApp get() = mySharedPreferences


    var listExcation = ArrayList<String>()

    fun errorDialog(
        title:String,
        code:Int,
        mySharedPreferences: MySharedPreferences?=null,
        onClick:(bool:Boolean)->Unit
    ){
        listExcation.add(title)
        dialogHelper.errorDialog(listExcation,code,mySharedPreferences){
            onClick.invoke(true)
        }
    }



    fun createChatDialog(
        adminName:String,
        list: List<ChatData>,
        onClick:(message:String)->Unit
    ){
        dialogHelper.messageDialog(adminName,list,onClick)
    }



    fun otherDialog(
        position:Int,
        appTheme: AppTheme,
        onClick:(isClick:Boolean)->Unit
    ){
        dialogHelper.otherDialog(position,appTheme,onClick)
    }


    fun createSettings(){
        try {
            val myAppSettings = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + mContext.packageName)
            )
            myAppSettings.addCategory(Intent.CATEGORY_DEFAULT)
            myAppSettings.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mActivity.startActivity(myAppSettings)
        }catch (e:Exception){
            errorDialog(e.message.toString(),-1){}
        }
    }


    fun callAdmin(phoneNumber:String){
        var intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${phoneNumber}")
        mActivity.startActivity(intent)
    }

    fun setImage(image:ImageView,imgUrl:String,corner:Float){
        image.load(imgUrl){
            placeholder(R.drawable.button_back)
            crossfade(AppConstant.CROSSFADE_BOOL)
            crossfade(AppConstant.CROSSFADE)
            transformations(RoundedCornersTransformation(corner))
        }
    }

    fun inputTextCreateKeyboard(editText:EditText){
        try {
            editText.requestFocus()
            if (editText.requestFocus()){
                val inputMethodManager = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, AppConstant.ZERO)
            }
        }catch (e:Exception){
            errorDialog(e.message.toString(),-1){}
        }
    }

    fun hideKeyboard(editText:EditText){
        try {
            val imm = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(editText.windowToken, 0)
        }catch (e:Exception){
         errorDialog(e.message.toString(),-1){}
        }
    }

    fun customToast(message:String){
        val toast = Toast(mContext)
        toast.duration = Toast.LENGTH_LONG
        val inflate = CustomToastBinding.inflate(mLayoutInflater)
        toast.view = inflate.root
        inflate.text.textApp(message)
        toast.show()
    }


    fun colorStatusAndNavigationBars(color:Int){
        mActivity.window?.statusBarColor = color
        mActivity.window?.navigationBarColor = color
    }



    fun call(
        phoneNumber: String
    ){
        PermissionX.init(activity)
            .permissions(Manifest.permission.CALL_PHONE)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, mContext.getString(R.string.permission_str), "OK", mContext.getString(R.string.cancel))
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    callAdmin(phoneNumber)
                } else {
                    createSettings()
                }
            }
    }

}


