package com.example.housemanagment.utils.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.housemanagment.R
import com.example.housemanagment.models.logout.LogOutRes
import com.example.housemanagment.utils.AppConstant
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.THREE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.responseState.ResponseState
import com.example.housemanagment.utils.uiController.UiController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import java.util.*


suspend inline fun <reified T> StateFlow<ResponseState<T>>.fetchResult(
    uiController: UiController,
    crossinline invokeSuccess: (T) -> Unit,
) {
    var count = ZERO
    this.collect { result ->
        when (result) {
            is ResponseState.Loading -> {
                uiController.showProgress()
            }
            is ResponseState.Success -> {
                uiController.hideProgress()
                val jsonObject = JSONObject(Gson().toJson(result.data))
                val parseData = jsonObject.parseJsonInClass(T::class.java)
                invokeSuccess.invoke(parseData)
            }
            is ResponseState.Error -> {
                uiController.hideProgress()
                if (count == ZERO){
                    uiController.error(result.errorCode.toLong(), result.message.toString())
                    count++
                }
            }
        }
    }
}


inline fun <reified T> JSONObject.parseJsonInClass(classData:Class<T>):T{
    val gson = GsonBuilder()
    return gson.create().fromJson(this.toString(),classData)
}


fun View.visible(){
    this.isVisible = true
}
fun View.gone(){
    this.isVisible = false
}


fun Button.visible(){
    this.isVisible = true
}

fun Button.gone(){
    this.isVisible = false
}

fun Button.enabledTrue(){
    this.isEnabled = true
}

fun Button.enabledFalse(){
    this.isEnabled = false
}

fun String?.isNullOrEmpty():Boolean{
    return this?.trim() != null && this.trim().isNotEmpty() && this.trim() != ""
}

fun String?.isNotNullOrEmpty():Boolean{
    return this?.trim() != null && this.trim().isNotEmpty() && this.trim() != ""
}



fun String?.isLength():Boolean{
    return this?.trim()?.length!! >= AppConstant.PASSWORD_LENGTH
}


fun ConstraintLayout.visible(){
    this.isVisible = true
}
fun ConstraintLayout.gone(){
    this.isVisible = false
}

fun LinearLayout.visible(){
    this.isVisible = true
}
fun LinearLayout.gone(){
    this.isVisible = false
}
fun BottomNavigationView.isSelected(position:Int){
    when(position){
        ZERO ->{
            this.menu.findItem(R.id.report).isChecked = true
        }
        ONE ->{
            this.menu.findItem(R.id.cash).isChecked = true
        }
        TWO ->{
            this.menu.findItem(R.id.users).isChecked = true
        }
        THREE ->{
            this.menu.findItem(R.id.settings).isChecked = true
        }
        else->{
            this.menu.findItem(R.id.report).isChecked = true
        }
    }
}

fun BottomNavigationView.clickData(itemId:Int):Int{
        return when(itemId){
          R.id.report ->{
             ZERO
          }
          R.id.cash ->{
              ONE
          }
          R.id.users ->{
              TWO
          }
          R.id.settings ->{
              THREE
          }
          else->{
              ZERO
          }
      }
}

fun View.loadAnimation(context: Context):Animation{
    val loadAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_view)
    this.animation = loadAnimation
    return loadAnimation
}

fun TextView.textApp(str:String){
    this.text = str
}

fun Double.format():String{
    val formatter =  NumberFormat.getNumberInstance(Locale.getDefault()).format(this)
    return formatter
}

fun <A: Activity> Activity.startNewActivity(activity:Class<A>){
    Intent(this,activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}