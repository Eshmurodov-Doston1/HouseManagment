package com.example.housemanagment.network.rePository

import com.example.housemanagment.network.auth.ApiService
import com.example.housemanagment.utils.baseRes.ResponseFetcher
import com.example.housemanagment.utils.responseState.ResponseState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val authService: ApiService,
    private val responseFetcher: ResponseFetcher.Base,
) {

    suspend fun <T : Any,K> methodePOST(fullUrl:String, data: T, headerMap: HashMap<String,String>):Flow<ResponseState<K?>>{
        return responseFetcher.getFlowResponseState(authService.methodePOST(fullUrl,data,headerMap))
    }

    suspend fun <K> methodePOSTnoBody(fullUrl:String,headerMap: HashMap<String,String>):Flow<ResponseState<K?>>{
        return responseFetcher.getFlowResponseState(authService.methodePOSTnoBody(fullUrl,headerMap))
    }


    suspend fun <K> methodeGET(fullUrl:String,headerMap: HashMap<String,String>):Flow<ResponseState<K?>> =
       responseFetcher.getFlowResponseState(authService.methodeGET(fullUrl,headerMap))


    suspend fun <T:Any,K> methodePUT(fullUrl:String,authReq: T,headerMap: HashMap<String,String>):Flow<ResponseState<K?>> =
        responseFetcher.getFlowResponseState(authService.methodePUT(fullUrl,authReq,headerMap))

    suspend fun <T:Any,K> methodeDELETE(fullUrl:String,authReq: T,headerMap: HashMap<String,String>):Flow<ResponseState<K?>> =
        responseFetcher.getFlowResponseState(authService.methodeDELETE(fullUrl,authReq,headerMap))

}