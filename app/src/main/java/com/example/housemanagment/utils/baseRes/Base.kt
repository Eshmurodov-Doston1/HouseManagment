package com.example.housemanagment.utils.baseRes

import android.accounts.NetworkErrorException
import android.util.Log
import com.example.housemanagment.utils.responseState.ResponseState
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.conn.ConnectTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

interface ResponseFetcher {
    fun <K> getFlowResponseState(response: Response<K>): Flow<ResponseState<K?>>
    fun <T> errorMessage(response: Response<T>?): String


    class Base @Inject constructor() : ResponseFetcher{
        override fun <K> getFlowResponseState(response: Response<K>) =
         flow {
                val flow = try {
                    coroutineScope {
                        if (response.isSuccessful)
                            ResponseState.Success(response.body())
                        else throw HttpException(response)
                    }
                } catch (e: IOException) {
                    ResponseState.Error(e.hashCode(), e.message)
                } catch (e: HttpException) {
                    ResponseState.Error(e.code(), errorMessage(e.response()))
                } catch (e: Exception) {
                    ResponseState.Error(e.hashCode(), e.message)
                }catch (e:IllegalArgumentException){
                    ResponseState.Error(e.hashCode(), e.message)
                }catch (e: ConnectTimeoutException){
                    ResponseState.Error(e.hashCode(), e.message)
                }catch (e:IllegalThreadStateException){
                    ResponseState.Error(e.hashCode(), e.message)
                }catch (e:InstantiationException){
                    ResponseState.Error(e.hashCode(), e.message)
                }catch (e:SocketTimeoutException){
                    ResponseState.Error(e.hashCode(), e.message)
                }catch (e: NetworkErrorException){
                    ResponseState.Error(e.hashCode(), e.message)
                }
                emit(flow)
            }.flowOn(Dispatchers.IO)


        override fun <T> errorMessage(response: Response<T>?): String {
             try {
                val jsonObject = JSONObject(response?.errorBody()?.string()!!)
                return if (jsonObject.has("message")) {
                    jsonObject.get("message").toString()
                } else {
                    // TODO: need to write message body
                    ""
                }
            } catch (e: Exception) {
                // TODO: need to write message body
                e.message ?: ""
            }
            return "/"
        }

    }
}
