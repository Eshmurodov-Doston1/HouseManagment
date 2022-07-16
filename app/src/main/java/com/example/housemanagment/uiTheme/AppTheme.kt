package com.example.housemanagment.uiTheme

import android.content.Context
import com.dolatkia.animatedThemeManager.AppTheme

interface AppTheme:AppTheme {
    fun backgroundColorApp(context: Context):Int
    fun backgroundColorTool(context: Context):Int
    fun textColorApp(context: Context):Int
    fun buttonColorApp(context: Context):Int
    fun lineColor(context: Context):Int
    fun itemButtonColor(context: Context):Int
    fun hintColor(context: Context):Int
}