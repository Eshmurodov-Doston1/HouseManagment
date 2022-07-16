package com.example.housemanagment.models.demoMenu.flat

import java.io.Serializable

data class Flat(
    var number:Long,
    var image:String?,
    var adminFullName:String?,
    var flatAddress:String,
    var status:String?,// this kredit or rasrochka
    var term:String?,
    var paid_for:String?,
    var paidOut:Double?,
    var left:Double?,
    var debt:Double?,
    var allSum:Double?,
    var statustContract:Int,
    var squaremetr:Double
):Serializable
