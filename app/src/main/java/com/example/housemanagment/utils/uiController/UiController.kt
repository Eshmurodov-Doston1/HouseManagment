package com.example.housemanagment.utils.uiController

interface UiController {
    fun showProgress()
    fun hideProgress()
    fun error(errorCode:Long,errorMessage:String)
}