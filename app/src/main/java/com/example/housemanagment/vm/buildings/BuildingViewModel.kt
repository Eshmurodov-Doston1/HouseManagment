package com.example.housemanagment.vm.buildings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.housemanagment.BuildConfig.BASE_URL
import com.example.housemanagment.database.repository.buildingRepo.BuildingRepo
import com.example.housemanagment.models.blockData.BlockData
import com.example.housemanagment.models.buildingData.BuildingData
import com.example.housemanagment.models.flat.FlatData
import com.example.housemanagment.models.flatData.HomeData
import com.example.housemanagment.models.house.HouseData
import com.example.housemanagment.models.money.Money
import com.example.housemanagment.network.rePository.ApiRepository
import com.example.housemanagment.utils.AppConstant
import com.example.housemanagment.utils.AppConstant.API
import com.example.housemanagment.utils.AppConstant.BUILDING_LIST
import com.example.housemanagment.utils.AppConstant.DOM_LIST
import com.example.housemanagment.utils.AppConstant.ERROR_NO_INTERNET
import com.example.housemanagment.utils.AppConstant.HOME_GET_LIST
import com.example.housemanagment.utils.AppConstant.HOUSE_LIST
import com.example.housemanagment.utils.AppConstant.HOUSE_SOLD_LIST
import com.example.housemanagment.utils.AppConstant.TRANSACTION_LIST
import com.example.housemanagment.utils.networkHelper.NetworkHelper
import com.example.housemanagment.utils.responseState.ResponseState
import com.example.housemanagment.utils.sharedPreference.MySharedPreferences
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpHeaders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildingViewModel @Inject constructor(
    private val networkHelper: NetworkHelper,
    private val apiRepository: ApiRepository,
    private val mySharedPreferences: MySharedPreferences,
    private val buildingRepo: BuildingRepo
):ViewModel() {
    val sharedPreferences get() = mySharedPreferences
    /** url get building company **/
    private val buildingCompanyUrl = "$BASE_URL$API$BUILDING_LIST"
    /** url get building Block **/

    // TODO: getData building list
    val buildingData:StateFlow<ResponseState<BuildingData?>> get() =_buildingData
    private var _buildingData = MutableStateFlow<ResponseState<BuildingData?>>(ResponseState.Loading)
    // TODO: getData block list
    val blockData:StateFlow<ResponseState<BlockData?>> get() =_blockData
    private var _blockData = MutableStateFlow<ResponseState<BlockData?>>(ResponseState.Loading)
    // TODO: getData Flat Data
    val flatData:StateFlow<ResponseState<FlatData?>> get() =_flatData
    private var _flatData = MutableStateFlow<ResponseState<FlatData?>>(ResponseState.Loading)
    // TODO: getData House Data
    val houseData:StateFlow<ResponseState<HouseData?>> get() =_houseData
    private var _houseData = MutableStateFlow<ResponseState<HouseData?>>(ResponseState.Loading)
    // TODO: getData Home Data
    val homeData:StateFlow<ResponseState<HomeData?>> get() =_homeData
    private var _homeData = MutableStateFlow<ResponseState<HomeData?>>(ResponseState.Loading)
    // TODO: getData Money Data
    val moneyData:StateFlow<ResponseState<Money?>> get() =_moneyData
    private var _moneyData = MutableStateFlow<ResponseState<Money?>>(ResponseState.Loading)

    fun getDataBuilding(){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                _buildingData.emit(ResponseState.Loading)
                apiRepository.methodeGET<BuildingData>(buildingCompanyUrl,getHeaderMap())
                    .catch { error-> _buildingData.emit(ResponseState.Error(error.hashCode(), error.localizedMessage)) }.collect{ response->
                    _buildingData.emit(response)
                }
            }else{
                _buildingData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }



    fun getDataBuildingBlock(id:Int){
        val buildingBlockUrl = "$BASE_URL$API$BUILDING_LIST/$id"
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                _blockData.emit(ResponseState.Loading)
                apiRepository.methodeGET<BlockData>(buildingBlockUrl,getHeaderMap())
                    .catch { error-> _blockData.emit(ResponseState.Error(error.hashCode(), error.localizedMessage)) }.collect{ response->
                        _blockData.emit(response)
                    }
            }else{
                _blockData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }

    fun getFlatData(id:Int){
        val flatBaseUrl = "$BASE_URL$API$DOM_LIST/$id"
        viewModelScope.launch {
            if(networkHelper.isNetworkConnected()){
                apiRepository.methodeGET<FlatData>(flatBaseUrl,getHeaderMap())
                    .catch { error->_flatData.emit(ResponseState.Error(error.hashCode(),error.message)) }.collect{ response->
                    _flatData.emit(response)
                }
            }else{
                _flatData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }


    fun getHouse(id:Int){
        val houseUrl = "$BASE_URL$API$HOUSE_LIST$id"
        viewModelScope.launch {
            if(networkHelper.isNetworkConnected()){
                apiRepository.methodeGET<HouseData>(houseUrl,getHeaderMap())
                    .catch { error->_flatData.emit(ResponseState.Error(error.hashCode(),error.message))}
                    .collect{response->_houseData.emit(response)}
            }else{
                _houseData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }

    fun getSoldHouse(){
       viewModelScope.launch {
           val houseSoldUrl = "$BASE_URL$API$HOUSE_SOLD_LIST"
           if (networkHelper.isNetworkConnected()){
               apiRepository.methodeGET<HouseData>(houseSoldUrl,getHeaderMap())
                   .catch { error->_flatData.emit(ResponseState.Error(error.hashCode(),error.message))}
                   .collect{response->_houseData.emit(response)}
           }else{
               _houseData.emit(ResponseState.Error(ERROR_NO_INTERNET))
           }
       }
    }



    fun getHomeData(id:Int){
        viewModelScope.launch {
            var urlHomeData = "$BASE_URL$API$HOME_GET_LIST$id"
            if (networkHelper.isNetworkConnected()){
                apiRepository.methodeGET<HomeData>(urlHomeData,getHeaderMap())
                    .catch { error->_homeData.emit(ResponseState.Error(error.hashCode(),error.message))}
                    .collect{response->_homeData.emit(response)}
            }else{
                _homeData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }


    fun getMoneyOperation(){
        viewModelScope.launch {
            val moneyOperationFullUrl="$BASE_URL$API$TRANSACTION_LIST"
            if (networkHelper.isNetworkConnected()){
                apiRepository.methodeGET<Money>(moneyOperationFullUrl,getHeaderMap())
                    .catch { error->_moneyData.emit(ResponseState.Error(error.hashCode(),error.message))}
                    .collect{response->_moneyData.emit(response)}
            }else{
                _moneyData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }

    fun getHeaderMap():HashMap<String,String>{
        var mapHeader = HashMap<String,String>()
        mapHeader[HttpHeaders.AUTHORIZATION] = "${AppConstant.TOKEN_TYPE} ${sharedPreferences.token}"
        mapHeader[HttpHeaders.CONTENT_TYPE] = AppConstant.JSON_APP
        mapHeader[HttpHeaders.ACCEPT] = AppConstant.JSON_APP
        return mapHeader
    }

}