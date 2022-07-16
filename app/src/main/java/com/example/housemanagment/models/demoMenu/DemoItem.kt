package com.example.housemanagment.models.demoMenu

import java.io.Serializable

data class DemoItem(
    var title:String,
    var quantity:Int,
    var pay:Double
):Serializable
