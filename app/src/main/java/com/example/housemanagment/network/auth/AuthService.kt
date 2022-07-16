package com.example.housemanagment.network.auth

import retrofit2.Response
import retrofit2.http.*

interface AuthService {

    @POST
    suspend fun <K> methodePOST(
        @Url url:String,
        @Body request: Any,
        @HeaderMap headerMap:HashMap<String,String>
    ):Response<K>

    @POST
    suspend fun <K> methodePOSTnoBody(
        @Url url:String,
        @HeaderMap headerMap:HashMap<String,String>
    ):Response<K>

    @GET
    suspend fun <K> methodeGET(
        @Url url:String,
        @HeaderMap headerMap:HashMap<String,String>
    ):Response<K>

    @PUT
    suspend fun <T,K> methodePUT(
        @Url url:String,
        @Body request:T,
        @HeaderMap headerMap:HashMap<String,String>
    ):Response<K>

    @DELETE
    suspend fun <T,K> methodeDELETE(
        @Url url:String,
        @Body request:T,
        @HeaderMap headerMap:HashMap<String,String>
    ):Response<K>
}