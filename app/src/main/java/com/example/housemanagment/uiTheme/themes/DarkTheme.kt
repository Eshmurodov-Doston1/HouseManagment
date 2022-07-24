package com.example.housemanagment.uiTheme.themes

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.housemanagment.R
import com.example.housemanagment.uiTheme.AppTheme

class DarkTheme:AppTheme {
    override fun backgroundColorApp(context: Context): Int {
        return ContextCompat.getColor(context, R.color.backgroundDarkColor)
    }

    override fun backgroundColorTool(context: Context): Int {
        return ContextCompat.getColor(context, R.color.backgroundDarkColor_Tool)
    }

    override fun textColorApp(context: Context): Int {
        return ContextCompat.getColor(context, R.color.textDarkColor)
    }

    override fun buttonColorApp(context: Context): Int {
        return ContextCompat.getColor(context, R.color.buttonDarkColor)
    }

    override fun lineColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.line_color_dark)
    }

    override fun itemButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.backgroundDarkColor_Tool)
    }

    override fun hintColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.hintDarkColor)
    }

    override fun itemCardColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.backgroundDarkColor_Tool)
    }

    override fun iconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.white)
    }

    override fun id(): Int {
        return 1
    }
}