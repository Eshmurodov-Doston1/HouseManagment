package com.example.housemanagment.vm.buildings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.housemanagment.BuildConfig.BASE_URL
import com.example.housemanagment.database.repository.buildingRepo.BuildingRepo
import com.example.housemanagment.models.activeContract.ResContract
import com.example.housemanagment.models.activeContract.SendActive
import com.example.housemanagment.models.blockData.BlockData
import com.example.housemanagment.models.bookingData.Booking
import com.example.housemanagment.models.bookingData.BookingRes
import com.example.housemanagment.models.buildingData.BuildingData
import com.example.housemanagment.models.flat.FlatData
import com.example.housemanagment.models.flatData.HomeData
import com.example.housemanagment.models.house.HouseData
import com.example.housemanagment.models.money.Money
import com.example.housemanagment.models.price.ResponseDataPrice
import com.example.housemanagment.models.price.SendPrice
import com.example.housemanagment.models.soldData.SoldData
import com.example.housemanagment.models.valutaCourse.CourseData
import com.example.housemanagment.network.rePository.ApiRepository
import com.example.housemanagment.utils.AppConstant
import com.example.housemanagment.utils.AppConstant.ACTIVE_CONTRACT
import com.example.housemanagment.utils.AppConstant.AGREEMENT
import com.example.housemanagment.utils.AppConstant.API
import com.example.housemanagment.utils.AppConstant.BOOKING
import com.example.housemanagment.utils.AppConstant.BUILDING_LIST
import com.example.housemanagment.utils.AppConstant.CBU_URL
import com.example.housemanagment.utils.AppConstant.DOM_LIST
import com.example.housemanagment.utils.AppConstant.ERROR_INTERNET
import com.example.housemanagment.utils.AppConstant.ERROR_NO_INTERNET
import com.example.housemanagment.utils.AppConstant.HOME_GET_LIST
import com.example.housemanagment.utils.AppConstant.HOUSE_LIST
import com.example.housemanagment.utils.AppConstant.HOUSE_SOLD_LIST
import com.example.housemanagment.utils.AppConstant.TRANSACTION_LIST
import com.example.housemanagment.utils.AppConstant.emptyMap
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
import java.io.IOException
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
    // TODO: getData Money Data
    val courseData:StateFlow<ResponseState<CourseData?>> get() =_courseData
    private var _courseData = MutableStateFlow<ResponseState<CourseData?>>(ResponseState.Loading)
    // TODO: Booking data
    val bookingData:StateFlow<ResponseState<BookingRes?>> get() =_bookingData
    private var _bookingData = MutableStateFlow<ResponseState<BookingRes?>>(ResponseState.Loading)
    // TODO: Sold Data
    val soldData:StateFlow<ResponseState<SoldData?>> get() =_soldData
    private var _soldData = MutableStateFlow<ResponseState<SoldData?>>(ResponseState.Loading)
    // TODO: Active or NoActive data
    val dataContract:StateFlow<ResponseState<ResContract?>> get() =_dataContract
    private var _dataContract = MutableStateFlow<ResponseState<ResContract?>>(ResponseState.Loading)
    // TODO: Active or NoActive data
    val flatListPrice:StateFlow<ResponseState<ResponseDataPrice?>> get() =_flatListPrice
    private var _flatListPrice = MutableStateFlow<ResponseState<ResponseDataPrice?>>(ResponseState.Loading)

    fun getDataBuilding(){
        viewModelScope.launch {
            if (networkHelper.isNetworkConnected()){
                _buildingData.emit(ResponseState.Loading)
                try {
                    apiRepository.methodeGET<BuildingData>(buildingCompanyUrl,getHeaderMap())
                        .catch { error-> _buildingData.emit(ResponseState.Error(error.hashCode(), error.localizedMessage)) }.collect{ response->
                           try {
                               _buildingData.emit(response)
                           }catch (e:Exception){
                               e.printStackTrace()
                           }
                        }
                }catch (e:Exception){
                    _buildingData.emit(ResponseState.Error(e.hashCode(),e.message))
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
                try {
                    apiRepository.methodeGET<BlockData>(buildingBlockUrl,getHeaderMap())
                        .catch { error-> _blockData.emit(ResponseState.Error(error.hashCode(), error.localizedMessage)) }.collect{ response->
                            _blockData.emit(response)
                        }
                }catch (e:Exception){
                    _blockData.emit(ResponseState.Error(e.hashCode(),e.message))
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
                _flatData.emit(ResponseState.Loading)
                try {
                    apiRepository.methodeGET<FlatData>(flatBaseUrl,getHeaderMap())
                        .catch { error->_flatData.emit(ResponseState.Error(error.hashCode(),error.message)) }.collect{ response->
                            _flatData.emit(response)
                        }
                }catch (e:Exception){
                    _flatData.emit(ResponseState.Error(e.hashCode(),e.message))
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
                _homeData.emit(ResponseState.Loading)
                try {
                    apiRepository.methodeGET<HouseData>(houseUrl,getHeaderMap())
                        .catch { error->_flatData.emit(ResponseState.Error(error.hashCode(),error.message))}
                        .collect{response->
                            _houseData.emit(response)
                        }
                }catch (e:Exception){
                    _houseData.emit(ResponseState.Error(e.hashCode(),e.message))
                }
            }else{
                _houseData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }

    fun getSoldHouse(){
       viewModelScope.launch {
           val houseSoldUrl = "$BASE_URL$API$HOUSE_SOLD_LIST"
           if (networkHelper.isNetworkConnected()){
               _flatData.emit(ResponseState.Loading)
               try {
                   apiRepository.methodeGET<HouseData>(houseSoldUrl,getHeaderMap())
                       .catch { error->_flatData.emit(ResponseState.Error(error.hashCode(),error.message))}
                       .collect{response->_houseData.emit(response)}
               }catch (e:Exception){
                   _houseData.emit(ResponseState.Error(e.hashCode(),e.message))
               }

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
                    .collect{response->
                        _homeData.emit(response)
                    }
            }else{
                _homeData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }


    fun getMoneyOperation(){
        viewModelScope.launch {
            val moneyOperationFullUrl="$BASE_URL$API$TRANSACTION_LIST"
            if (networkHelper.isNetworkConnected()){
                _moneyData.emit(ResponseState.Loading)
                try {
                    apiRepository.methodeGET<Money>(moneyOperationFullUrl,getHeaderMap())
                        .catch { error->_moneyData.emit(ResponseState.Error(error.hashCode(),error.message))}
                        .collect{response->_moneyData.emit(response)}
                }catch (e:Exception){
                    _moneyData.emit(ResponseState.Error(e.hashCode(),e.message))
                }
            }else{
                _moneyData.emit(ResponseState.Error(ERROR_NO_INTERNET))
            }
        }
    }

    /**Url Cbu Course**/
    private val cbuCourse = CBU_URL
    fun getCbuData() = viewModelScope.launch {
        if (networkHelper.isNetworkConnected()){
            _courseData.emit(ResponseState.Loading)
            try {
                apiRepository.methodeGET<CourseData>(cbuCourse, emptyMap).collect{ response->
                    _courseData.emit(response)
                }
            }catch (e:Exception){
                _courseData.emit(ResponseState.Error(e.hashCode(),e.message))
            }
        }else{
            _courseData.emit(ResponseState.Error(ERROR_NO_INTERNET))
        }
    }


    fun bookingSave(booking: Booking,id:Int) = viewModelScope.launch {
        /**Booking Url data**/
        val bookingUrl = "$BASE_URL$API$BOOKING$id"
        if (networkHelper.isNetworkConnected()){
            try {
                _bookingData.emit(ResponseState.Loading)
                apiRepository.methodePOST<Booking,BookingRes>(bookingUrl,booking,getHeaderMap())
                    .collect{ response->
                        _bookingData.emit(response)
                    }
            }catch (e:IOException){
                _courseData.emit(ResponseState.Error(ERROR_INTERNET))
            }catch (e:Exception){
                _courseData.emit(ResponseState.Error(e.hashCode(),e.message))
            }
        }else{
            _bookingData.emit(ResponseState.Error(ERROR_NO_INTERNET))
        }
    }


    /** url soldData Director **/
    private val urlAgreement = "$BASE_URL$API$AGREEMENT"
    fun soldData() = viewModelScope.launch {
        if (networkHelper.isNetworkConnected()){
            _courseData.emit(ResponseState.Loading)
            try{
                apiRepository.methodeGET<SoldData>(urlAgreement,getHeaderMap()).collect{ response-> _soldData.emit(response) }
            }catch (e:IOException){
                _courseData.emit(ResponseState.Error(ERROR_INTERNET))
            }catch (e:Exception){
                _courseData.emit(ResponseState.Error(e.hashCode(),e.message))
            }
        }else{
            _soldData.emit(ResponseState.Error(ERROR_NO_INTERNET))
        }
    }



    fun activeContract(sendActive: SendActive,id:Int) = viewModelScope.launch {
        val activeContractUrl = "$BASE_URL$API$ACTIVE_CONTRACT$id"
        if (networkHelper.isNetworkConnected()){
            _dataContract.emit(ResponseState.Loading)
            try {
                apiRepository.methodePOST<SendActive,ResContract>(activeContractUrl,sendActive,getHeaderMap())
                    .collect{ response-> _dataContract.emit(response)}
            }catch (e:IOException){
                _dataContract.emit(ResponseState.Error(ERROR_INTERNET))
            }catch (e:Exception){
                _dataContract.emit(ResponseState.Error(e.hashCode(),e.message))
            }
        }else{
            _dataContract.emit(ResponseState.Error(ERROR_NO_INTERNET))
        }
    }

    fun flatPrice(sendPrice: SendPrice,id:Int) = viewModelScope.launch {
        val activeContractUrl = "$BASE_URL$API$HOUSE_LIST$id"
        if (networkHelper.isNetworkConnected()){
            _flatListPrice.emit(ResponseState.Loading)
            try {
                apiRepository.methodePUT<SendPrice,ResponseDataPrice>(activeContractUrl,sendPrice,getHeaderMap())
                    .collect{ response-> _flatListPrice.emit(response)}
            }catch (e:IOException){
                _flatListPrice.emit(ResponseState.Error(ERROR_INTERNET))
            }catch (e:Exception){
                _flatListPrice.emit(ResponseState.Error(e.hashCode(),e.message))
            }
        }else{
            _flatListPrice.emit(ResponseState.Error(ERROR_NO_INTERNET))
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