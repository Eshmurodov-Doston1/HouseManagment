package com.example.housemanagment.adapters.rvAdapter.child

import androidx.annotation.LayoutRes

interface HolderChild<T> {
    fun onBind(
        data: T,
        position: Int,
        @LayoutRes layoutRes: Int)
}