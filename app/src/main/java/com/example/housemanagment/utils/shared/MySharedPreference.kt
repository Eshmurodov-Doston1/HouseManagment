package com.example.housemanagment.utils.shared

import android.content.Context
import android.content.SharedPreferences
import com.example.housemanagment.utils.AppConstant.EMPTY
import com.example.housemanagment.utils.AppConstant.SHARED_NAME
import com.example.housemanagment.utils.AppConstant.TOKEN
import javax.inject.Inject

/** this is class SharedPreference create and save storage data accessToken and refreshToken and token type **/


class MySharedPreference @Inject constructor(context: Context) {
  private val mode = Context.MODE_PRIVATE
  private val preferences: SharedPreferences = context.getSharedPreferences(SHARED_NAME, mode)

  private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
  }

  fun clear() {
    preferences.edit().clear().apply()
  }

  var token: String?
    get() = preferences.getString(TOKEN, EMPTY)
    set(value) = preferences.edit {
      if (value != null) {
        it.putString(TOKEN, value)
      }
    }
}