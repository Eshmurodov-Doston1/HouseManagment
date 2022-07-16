package com.example.housemanagment.models.demoMenu

import java.io.Serializable


data class DemoMenu(
    var title:String,
    var users:String,
    var icon:Int,
    var colorCard:Int,
    var cateGory:Int
):Serializable
