package com.example.housemanagment.adapters.rvAdapter

import android.content.Context
import androidx.annotation.LayoutRes

interface Holder<T> {
    fun onBind(
        data: T,
        position: Int,
        @LayoutRes layoutRes: Int,
        onItemClickListener: RvGenericAdapter.OnItemClickListener<T>,
        context: Context,
        onClick:(T:Any)->Unit)
}