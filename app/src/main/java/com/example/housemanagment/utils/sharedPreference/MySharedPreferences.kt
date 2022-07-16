package com.example.housemanagment.utils.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.example.housemanagment.utils.AppConstant.COMPANY_NAME
import com.example.housemanagment.utils.AppConstant.DEFAULT_THEME
import com.example.housemanagment.utils.AppConstant.EMPTY
import com.example.housemanagment.utils.AppConstant.THEME
import com.example.housemanagment.utils.AppConstant.TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MySharedPreferences @Inject constructor(
    @ApplicationContext var context: Context
) {
    private val NAME = COMPANY_NAME
    private val MODE = Context.MODE_PRIVATE
    private val sharedPreferences:SharedPreferences = context.getSharedPreferences(NAME,MODE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun clear() {
        sharedPreferences.edit().remove(TOKEN).apply()
    }

    /** Theme application **/
    var theme:Boolean?
    get() = sharedPreferences.getBoolean(THEME, DEFAULT_THEME)
    set(value) = sharedPreferences.edit {
        if (value!=null){
            it.putBoolean(THEME,value)
        }
    }

    /** Token application **/
    var token:String?
        get() = sharedPreferences.getString(TOKEN, EMPTY)
        set(value) = sharedPreferences.edit {
            if (value!=null){
                it.putString(TOKEN,value)
            }
        }
}