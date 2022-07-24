package com.example.housemanagment.adapters.rvAdapter

import android.content.Context
import androidx.annotation.LayoutRes
import com.example.housemanagment.uiTheme.AppTheme

interface Holder<T> {
    fun onBind(
        data: T,
        position: Int,
        @LayoutRes layoutRes: Int,
        onItemClickListener: RvGenericAdapter.OnItemClickListener<T>,
        context: Context,
        appTheme: AppTheme?,
        onClick:(T:Any)->Unit)
}