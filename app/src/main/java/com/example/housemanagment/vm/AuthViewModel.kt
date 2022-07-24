package com.example.housemanagment.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.housemanagment.BuildConfig.BASE_URL
import com.example.housemanagment.database.entity.userEntity.UserEntity
import com.example.housemanagment.database.repository.AuthDaoRepository
import com.example.housemanagment.models.auth.AuthResponse
import com.example.housemanagment.models.auth.auhtReq.AuthReq
import com.example.housemanagment.models.logout.LogOutRes
import com.example.housemanagment.network.rePository.ApiRepository
import com.example.housemanagment.utils.AppConstant.API
import com.example.housemanagment.utils.AppConstant.ERROR_NO_INTERNET
import com.example.housemanagment.utils.AppConstant.JSON_APP
import com.example.housemanagment.utils.AppConstant.LOGIN
import com.example.housemanagment.utils.AppConstant.LOGOUT
import com.example.housemanagment.utils.AppConstant.TOKEN_TYPE
import com.example.housemanagment.utils.networkHelper.NetworkHelper
import com.example.housemanagment.utils.responseState.ResponseState
import com.example.housemanagment.utils.sharedPreference.MySharedPreferences
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpHeaders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val authRepository: ApiRepository,
    private val mySharedPreferences: MySharedPreferences,
    private val authDaoRepository: AuthDaoRepository
):ViewModel(){
    val sharedPreferences:MySharedPreferences = mySharedPreferences
    /** auth Full Url **/
    private val fullAuthUrl = "${BASE_URL}${API}${LOGIN}"

    /** House Auth **/
    val authData:StateFlow<ResponseState<AuthResponse?>> get() = _authData
    private val _authData = MutableStateFlow<ResponseState<AuthResponse?>>(ResponseState.Loading)

    /** User Logout **/
    val logOut:StateFlow<ResponseState<LogOutRes?>> get() = _logOut
    private val _logOut = MutableStateFlow<ResponseState<LogOutRes?>>(ResponseState.Loading)

    // TODO: AuthHouse methode
    fun authData(authReq: AuthReq){
        viewModelScope.launch {
            if(networkHelper.isNetworkConnected()){
                _authData.emit(ResponseState.Loading)
                var mapHeader = HashMap<String,String>()
                mapHeader[HttpHeaders.CONTENT_TYPE] = JSON_APP
                mapHeader[HttpHeaders.ACCEPT] = JSON_APP
                authRepository.methodePOST<AuthReq,AuthResponse>(fullAuthUrl,authReq,mapHeader).
                collect{ response->
                    _authData.emit(response)
                }
            }else{
                _authData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }

    /** Logout methode **/
    fun logOut(){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                _logOut.emit(ResponseState.Loading)
                authRepository.methodePOSTnoBody<LogOutRes>("$BASE_URL$API$LOGOUT",getHeaderMap()).collect{ response->
                        _logOut.emit(response)
                    }
            }else{
                _logOut.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }





    /** userData table operation **/
    suspend fun saveUserEntity(userEntity: UserEntity) =
          authDaoRepository.saveUser(userEntity)

    suspend fun updateUserEntity(userEntity: UserEntity) =
        authDaoRepository.updateUserEntity(userEntity)

    suspend fun deleteUserEntity(userEntity: UserEntity) =
        authDaoRepository.deleteUserEntity(userEntity)

    fun getUserEntity():UserEntity =  authDaoRepository.getUserEntity()

    suspend fun deleteUserTable() = authDaoRepository.deleteTableUser()

    /** getHeader Auth **/
    fun getHeaderMap():HashMap<String,String>{
        var mapHeader = HashMap<String,String>()
        mapHeader[HttpHeaders.AUTHORIZATION] = "$TOKEN_TYPE ${sharedPreferences.token}"
        mapHeader[HttpHeaders.CONTENT_TYPE] = JSON_APP
        mapHeader[HttpHeaders.ACCEPT] = JSON_APP
        return mapHeader
    }


}