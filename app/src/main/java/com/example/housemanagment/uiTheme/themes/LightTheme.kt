package com.example.housemanagment.uiTheme.themes

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.housemanagment.R
import com.example.housemanagment.uiTheme.AppTheme

class LightTheme:AppTheme {
    override fun backgroundColorApp(context: Context): Int {
        return ContextCompat.getColor(context, R.color.backgroundColor)
    }

    override fun backgroundColorTool(context: Context): Int {
        return ContextCompat.getColor(context, R.color.backgroundColor_Tool)
    }

    override fun textColorApp(context: Context): Int {
        return ContextCompat.getColor(context, R.color.textColor)
    }

    override fun buttonColorApp(context: Context): Int {
        return ContextCompat.getColor(context, R.color.buttonColor)
    }

    override fun lineColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.line_color)
    }

    override fun itemButtonColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.backgroundColor_Tool)
    }

    override fun hintColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.hintColor)
    }

    override fun id(): Int {
        return 0
    }
}