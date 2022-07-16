package com.example.housemanagment.presentation.pages.sectorFlat

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.adapterFlat.MainAdapterFlat
import com.example.housemanagment.databinding.FragmentSectorFlatBinding
import com.example.housemanagment.models.demoMenu.flat.Flat
import com.example.housemanagment.models.demoMenu.place.PlaceData
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.utils.extension.visible
import com.permissionx.guolindev.PermissionX


private const val ARG_PARAM1 = "placeData"

class SectorFlatFragment : BasePage(R.layout.fragment_sector_flat) {
    private var placeData: PlaceData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            placeData = it.getSerializable(ARG_PARAM1) as PlaceData
        }
    }
    private var _binidng:FragmentSectorFlatBinding?=null
    private val binding get() = _binidng!!
    private val appCompositionRoot get() = (activity as MainActivity).appCompositionRoot
    private lateinit var mainAdapterFlat:MainAdapterFlat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binidng = FragmentSectorFlatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            shimmerInclude.shimmer.startShimmer()
            Handler(Looper.getMainLooper()).postDelayed({
                shimmerInclude.shimmer.stopShimmer()
                shimmerInclude.consSimmer.gone()
                consToolbar.visible()
                rvFlat.visible()
            },2000)

            toolbarText.textApp(placeData?.name.toString())
            mainAdapterFlat = MainAdapterFlat(getAllflatData(),object:MainAdapterFlat.OnItemClickListener{
                override fun onItemClick(flat: Flat, position: Int) {
                   appCompositionRoot.screenNavigator.createFragmentFlatData(flat)
                }

                override fun onItemClickCall(flat: Flat, position: Int) {
                    PermissionX.init(activity)
                        .permissions(Manifest.permission.CALL_PHONE)
                        .onExplainRequestReason { scope, deniedList ->
                            scope.showRequestReasonDialog(deniedList, appCompositionRoot.mContext.getString(R.string.permission_str), "OK", appCompositionRoot.mContext.getString(R.string.cancel))
                        }
                        .request { allGranted, grantedList, deniedList ->
                            if (allGranted) {
                              appCompositionRoot.callAdmin("+998901277233")
                            } else {
                                appCompositionRoot.createSettings()
                            }
                        }
                }

                override fun onItemClickCallSMS(flat: Flat, position: Int) {
                    appCompositionRoot.createChatDialog(flat.adminFullName.toString(),getChatData()) {
                        // TODO: Send message in backend
                        Log.e("MesssageData", it)
                    }
                }
            },appCompositionRoot)
            rvFlat.adapter = mainAdapterFlat


            back.setOnClickListener {
                appCompositionRoot.screenNavigator.popBackStack()
            }
        }
    }
}