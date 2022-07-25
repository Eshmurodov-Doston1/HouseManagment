package com.example.housemanagment.network.auth

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST
    suspend fun <K> methodePOST(
        @Url url:String,
        @Body request: Any?,
        @HeaderMap headerMap:HashMap<String,String>,
        @QueryMap mapQuery:HashMap<String,String> = HashMap()
    ):Response<K>

    @GET
    suspend fun <K> methodeGET(
        @Url url:String,
        @HeaderMap headerMap:HashMap<String,String>,
        @QueryMap mapQuery:HashMap<String,String> = HashMap()
    ):Response<K>

    @PUT
    suspend fun <K> methodePUT(
        @Url url:String,
        @Body request:Any?=null,
        @HeaderMap headerMap:HashMap<String,String>
    ):Response<K>

    @DELETE
    suspend fun <K> methodeDELETE(
        @Url url:String,
        @Body request:Any,
        @HeaderMap headerMap:HashMap<String,String>,
        @QueryMap mapQuery:HashMap<String,String> = HashMap()
    ):Response<K>
}