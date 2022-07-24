package com.example.housemanagment.utils

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.housemanagment.R
import com.example.housemanagment.models.blockData.Block
import com.example.housemanagment.models.buildingData.Building
import com.example.housemanagment.models.demoMenu.DemoItem
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.models.demoMenu.flat.Flat
import com.example.housemanagment.models.demoMenu.place.PlaceData
import com.example.housemanagment.models.house.House

class ScreenNavigator(
    private val navController: NavController
) {



    fun createScreenDocument(demoMenu: DemoMenu){
        var bundle = Bundle()
        bundle.putSerializable("demoMenu",demoMenu)
        navController.navigate(R.id.action_mainFragment_to_documentFragment,bundle,animationCreatePage())
    }
    
    fun createCompany(building: Building){
        var bundle = Bundle()
        bundle.putSerializable("building",building)
        navController.navigate(R.id.action_mainFragment_to_companyFragment,bundle,animationCreatePage())
    }


    fun createSectorFlat(flat: com.example.housemanagment.models.flat.Flat){
        var bundle = Bundle()
        bundle.putSerializable("flat",flat)
        navController.navigate(R.id.action_housesFragment_to_sectorFlatFragment,bundle,animationCreatePage())
    }

    fun createCompanyWithDocument(placeData: PlaceData){
        var bundle = Bundle()
        bundle.putSerializable("placeData",placeData)
        navController.navigate(R.id.action_documentFragment_to_companyFragment2,bundle,animationCreatePage())
    }


    fun createFlatScreen(block: Block){
        var bundle = Bundle()
        bundle.putSerializable("block",block)
        navController.navigate(R.id.action_companyFragment_to_housesFragment,bundle,animationCreatePage())
    }

    fun createFragmentFlatData(house: House){
        var bundle = Bundle()
        bundle.putSerializable("house",house)
        navController.navigate(R.id.action_sectorFlatFragment_to_flatDataFragment,bundle,animationCreatePage())
    }

    fun animationCreatePage() = NavOptions.Builder()
            .setEnterAnim(R.anim.enter)
            .setExitAnim(R.anim.exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .setPopExitAnim(R.anim.pop_exit).build()


    fun popBackStack(){
        navController.popBackStack()
    }
}