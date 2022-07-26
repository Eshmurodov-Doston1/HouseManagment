package com.example.housemanagment.utils

object AppConstant {
    const val PASSWORD_LENGTH = 8
    const val ZERO = 0
    const val ONE = 1
    const val TWO = 2
    const val THREE = 3
    const val FOUR = 4
    const val CROSSFADE = 400
    const val CROSSFADE_BOOL = true
    const val COMPANY_NAME = "HouseManagment"
    const val THEME = "theme"
    const val DEFAULT_THEME = false
    const val DATABASE_NAME = "app.db"
    const val SHARED_NAME = "house"
    const val TOKEN = "token"
    const val EMPTY=""
    const val ERROR_NO_INTERNET= -2
    /**TokenType**/
    const val TOKEN_TYPE = "Bearer"
    /** Api All Paths or url text **/
    const val API= "/api"
    const val LOGIN="/login"
    const val LOGOUT="/logout"
    /**Building.kt list**/
    const val BUILDING_LIST = "/building/list"
    const val DOM_LIST = "/dom/list"
    const val HOUSE_LIST = "/house/list/"
    const val HOUSE_SOLD_LIST = "/house/sold"
    const val HOME_GET_LIST = "/house/get/"
    const val TRANSACTION_LIST = "/transaction/index"
    const val TYPE_HTTP="Content-Type"
    const val JSON_APP = "application/json"
    const val ACCEPT = "Accept"
    const val UN_AUTHORIZATION = 401

    const val CLIENT_CODE_START = 400
    const val CLIENT_CODE_END = 499

    const val SERVER_CODE_START = 500
    const val SERVER_CODE_END = 599

}