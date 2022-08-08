package com.example.housemanagment.presentation.pages.reportPage.documnet.agreement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.databinding.FragmentAgreementBinding
import com.example.housemanagment.models.activeContract.SendActive
import com.example.housemanagment.models.soldData.Sold
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.EMPTY
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.fetchResult
import com.example.housemanagment.utils.extension.format
import com.example.housemanagment.utils.extension.noData
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.vm.buildings.BuildingViewModel
import kotlinx.coroutines.launch


private const val AGREEMENT = "sold"
class AgreementFragment : BasePage(R.layout.fragment_agreement) {
    private var sold: Sold? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
         sold = it.getSerializable(AGREEMENT) as  Sold
        }
    }
    private var _binding:FragmentAgreementBinding?=null
    private val binding get() = _binding!!
    private val buildingViewModel:BuildingViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentAgreementBinding.inflate(inflater,container,false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            // TODO: Client data
            var fullName:String?=appCompositionRoot.getNoData()
            var address:String?=appCompositionRoot.getNoData()
            var pnfl:String?=appCompositionRoot.getNoData()
            var passSeries:String?=appCompositionRoot.getNoData()
            var passNumber:String?=appCompositionRoot.getNoData()
            var email:String?=appCompositionRoot.getNoData()
            var phone:String?=appCompositionRoot.getNoData()
            var second_phone:String?=appCompositionRoot.getNoData()
            if (sold?.client!=null){
              fullName = sold?.client?.fullName
              address = sold?.client?.address
              pnfl = sold?.client?.pnfl
              passSeries = sold?.client?.passSeries
              passNumber = sold?.client?.passNumber
              email = sold?.client?.email
              phone = sold?.client?.phone
              second_phone = sold?.client?.second_phone
            }

            clientName.textApp("${getStringData(R.string.full_name)} ${fullName}")
            clientAddress.textApp("${getStringData(R.string.address)} ${address}")
            clientPnfl.textApp("${getStringData(R.string.pnfl)} ${pnfl}")
            clientPassport.textApp("${getStringData(R.string.pnfl)} $passSeries${passNumber}")
            clientEmail.textApp("${getStringData(R.string.email)} $email")
            clientPhone1.textApp("${getStringData(R.string.phone_number)} +$phone")
            clientPhone2.textApp("${getStringData(R.string.phone_number)} +$second_phone")

            // TODO: Building data

            var buildingNameA:String?=appCompositionRoot.getNoData()
            var addressBuilding:String?=appCompositionRoot.getNoData()

            if (sold?.building!=null){
                buildingNameA = sold?.building?.name
                addressBuilding = sold?.building?.address
            }
            buildingName.textApp("${getStringData(R.string.client_name)} $buildingNameA")
            buildingAddress.textApp("${getStringData(R.string.address)} $addressBuilding")
            // TODO:  Block Data
            var blockNameAp:String?=appCompositionRoot.getNoData()
            if (sold?.blok!=null){
                blockNameAp = sold?.blok?.name
            }
            blockName.textApp("${getStringData(R.string.client_name)} $blockNameAp")
            // TODO: Dom data
            var domNameApp:String?=appCompositionRoot.getNoData()
            var floorFlatData:String?=appCompositionRoot.getNoData()
            if (sold?.dom!=null){
                domNameApp = sold?.dom?.name
                floorFlatData = sold?.dom?.floor
            }
            domName.textApp("${getStringData(R.string.client_name)} $domNameApp")
            domFloor.textApp("${getStringData(R.string.build)} ${floorFlatData} ${getStringData(R.string.etaj)}")
            // TODO: Flat data
            var houseNumber:String?=appCompositionRoot.getNoData()
            var houseRooms:String?=appCompositionRoot.getNoData()
            var houseArea:String?=appCompositionRoot.getNoData()
            var housePrice:Double?=0.0
            if (sold?.house!=null){
                houseNumber = sold?.house?.number
                houseRooms = sold?.house?.rooms
                houseArea = sold?.house?.area
                housePrice = sold?.house?.price
            }
            flatName.textApp("${getStringData(R.string.flat_number)} ${getStringData(R.string.number)} $houseNumber")
            flatRooms.textApp("${getStringData(R.string.room_count)} $houseRooms")
            flatArea.textApp("${getStringData(R.string.apartment_area)} $houseArea ${getStringData(R.string.area_house)}")
            flatPrice.textApp("${getStringData(R.string.flat_area)} ${getStringData(R.string.flat_summa)} $housePrice")
            // TODO: Pay
            var summaType:String?=null
            if (sold?.currency==ZERO){
                summaType = getStringData(R.string.money_type_us)
            }else if (sold?.currency == ONE){
                summaType = getStringData(R.string.money_type)
            }
            flatSumma.textApp("${getStringData(R.string.total_price)} ${sold?.summa?.toDouble()?.format()} ${summaType.toString()}")
            initialPay.textApp("${getStringData(R.string.initial_paid)} ${sold?.initial_paid?.toDouble()?.format()} ${summaType.toString()}")
            paid.textApp("${getStringData(R.string.paid_data)} ${sold?.paid?.toDouble()?.format()} ${summaType.toString()}")
            loan.textApp("${getStringData(R.string.loan)} ${sold?.loan?.toDouble()?.format()} ${summaType.toString()}")
            month.textApp("${getStringData(R.string.month)} ${sold?.month}")

        }
    }
    override fun syncTheme(appTheme: AppTheme) {
        super.syncTheme(appTheme)
        val appTheme1 = appTheme as com.example.housemanagment.uiTheme.AppTheme
        binding.apply {
            cancelBtn.setOnClickListener {
                contractMethode(ZERO,sold?.id!!,appTheme1, TWO)
            }
            okBtn.setOnClickListener {
                contractMethode(ONE,sold?.id!!,appTheme1, ONE)
            }
            clientName.setTextColor(appTheme.textColorApp(requireContext()))
            clientAddress.setTextColor(appTheme.textColorApp(requireContext()))
            clientPnfl.setTextColor(appTheme.textColorApp(requireContext()))
            clientPassport.setTextColor(appTheme.textColorApp(requireContext()))
            clientEmail.setTextColor(appTheme.textColorApp(requireContext()))
            clientPhone1.setTextColor(appTheme.textColorApp(requireContext()))
            clientPhone2.setTextColor(appTheme.textColorApp(requireContext()))

            buildingName.setTextColor(appTheme.textColorApp(requireContext()))
            buildingAddress.setTextColor(appTheme.textColorApp(requireContext()))

            blockName.setTextColor(appTheme.textColorApp(requireContext()))

            domName.setTextColor(appTheme.textColorApp(requireContext()))
            domFloor.setTextColor(appTheme.textColorApp(requireContext()))

            flatName.setTextColor(appTheme.textColorApp(requireContext()))
            flatRooms.setTextColor(appTheme.textColorApp(requireContext()))
            flatArea.setTextColor(appTheme.textColorApp(requireContext()))
            flatPrice.setTextColor(appTheme.textColorApp(requireContext()))


            flatSumma.setTextColor(appTheme.textColorApp(requireContext()))
            initialPay.setTextColor(appTheme.textColorApp(requireContext()))
            paid.setTextColor(appTheme.textColorApp(requireContext()))
            loan.setTextColor(appTheme.textColorApp(requireContext()))
            month.setTextColor(appTheme.textColorApp(requireContext()))

            numberAndHome.setTextColor(appTheme1.textColorApp(requireContext()))
            buildingTv.setTextColor(appTheme1.textColorApp(requireContext()))
            blockTv.setTextColor(appTheme1.textColorApp(requireContext()))
            domTv.setTextColor(appTheme1.textColorApp(requireContext()))
            flatTv.setTextColor(appTheme1.textColorApp(requireContext()))
            summaTv.setTextColor(appTheme1.textColorApp(requireContext()))
        }
    }

    fun contractMethode(active:Int,id:Int,appTheme: com.example.housemanagment.uiTheme.AppTheme,dialogType:Int){
        appCompositionRoot.otherDialog(dialogType,appTheme){
            if (it){
                buildingViewModel.activeContract(sendActive = SendActive(active.toString()), id)
                launch {
                    buildingViewModel.dataContract.fetchResult(appCompositionRoot.uiControllerApp){ result->
                        appCompositionRoot.screenNavigator.popBackStack()
                    }
                }
            }
        }
    }

    fun getStringData(data:Int):String{
        return requireActivity().getString(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}