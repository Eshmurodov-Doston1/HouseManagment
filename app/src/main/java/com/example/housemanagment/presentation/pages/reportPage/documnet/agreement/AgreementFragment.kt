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
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.fetchResult
import com.example.housemanagment.utils.extension.format
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
            clientName.textApp("${getStringData(R.string.full_name)} ${sold?.client?.fullName}")
            clientAddress.textApp("${getStringData(R.string.address)} ${sold?.client?.address}")
            clientPnfl.textApp("${getStringData(R.string.pnfl)} ${sold?.client?.pnfl}")
            clientPassport.textApp("${getStringData(R.string.pnfl)} ${sold?.client?.passSeries}${sold?.client?.passNumber}")
            clientEmail.textApp("${getStringData(R.string.email)} ${sold?.client?.email}")
            clientPhone1.textApp("${getStringData(R.string.phone_number)} +${sold?.client?.phone}")
            clientPhone2.textApp("${getStringData(R.string.phone_number)} +${sold?.client?.second_phone}")
            // TODO: Building data
            buildingName.textApp("${getStringData(R.string.client_name)} ${sold?.building?.name.toString()}")
            buildingAddress.textApp("${getStringData(R.string.address)} ${sold?.building?.address}")
            // TODO:  Block Data
            blockName.textApp("${getStringData(R.string.client_name)} ${sold?.blok?.name.toString()}")
            // TODO: Dom data
            domName.textApp("${getStringData(R.string.client_name)} ${sold?.dom?.name.toString()}")
            domFloor.textApp("${getStringData(R.string.build)} ${sold?.dom?.floor.toString()} ${getStringData(R.string.etaj)}")
            // TODO: Flat data
            flatName.textApp("${getStringData(R.string.flat_number)} ${getStringData(R.string.number)} ${sold?.house?.number.toString()}")
            flatRooms.textApp("${getStringData(R.string.room_count)} ${sold?.house?.rooms.toString()}")
            flatArea.textApp("${getStringData(R.string.apartment_area)} ${sold?.house?.area.toString()} ${getStringData(R.string.area_house)}")
            flatPrice.textApp("${getStringData(R.string.flat_area)} ${getStringData(R.string.flat_summa)} ${sold?.house?.price?.toDouble()?.format()}")
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