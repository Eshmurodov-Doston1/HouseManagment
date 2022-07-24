package com.example.housemanagment.adapters.rvAdapter.adapterFlat

import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.zoomy.Zoomy
import com.example.housemanagment.R
import com.example.housemanagment.databinding.ItemChildFlatBinding
import com.example.housemanagment.models.house.House
import com.example.housemanagment.uiTheme.AppTheme
import com.example.housemanagment.utils.*
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.*
import java.text.SimpleDateFormat
import java.util.*


class ChildAdapterFlat(
    private val listFlat:ArrayList<House>,
    private val onItemClickListener: OnItemClickListener,
    private val compositionRoot: AppCompositionRoot,
    private val appTheme: AppTheme
):RecyclerView.Adapter<ChildAdapterFlat.Vh>() {
    inner class Vh(var itemChildFlatBinding: ItemChildFlatBinding):RecyclerView.ViewHolder(itemChildFlatBinding.root){
        fun onBind(house: House,position: Int){
            try {
               viewTheme(appTheme,itemChildFlatBinding,itemView)

                itemChildFlatBinding.cardChild.setOnClickListener {
                    onItemClickListener.onItemClick(house,position)
                }

                itemChildFlatBinding.callBtn.setOnClickListener {
                    onItemClickListener.onItemClickCall(house, position)
                }
                itemChildFlatBinding.chatBtn.setOnClickListener {
                    onItemClickListener.onItemClickCallSMS(house,position)
                }

                val builder: Zoomy.Builder = Zoomy.Builder(compositionRoot.mActivity)
                    .target(itemChildFlatBinding.image)
                    .enableImmersiveMode(false)
                    .animateZooming(true)
                builder.register()


                itemChildFlatBinding.apply {
//                    if (house.adminFullName.isNotNullOrEmpty()){
//                        adminName.textApp(house.adminFullName.toString())
//                    }
//                    addressText.textApp(house.flatAddress)
//                    if (house.status.isNotNullOrEmpty()){
//                        purchasedText.textApp(house.status.toString())
//                    }
//                    if (house.term.isNotNullOrEmpty()){
//                        termText.textApp(house.term.toString())
//                    }
//                    if (house.paid_for.isNotNullOrEmpty())
//                    {
//                        paidForText.textApp(house.paid_for.toString())
//                    }
//                    if (house.paidOut?.format().isNotNullOrEmpty())
//                    {
//                        paidOutText.textApp(house.paidOut?.format().toString())
//                    }
//                    if (house.left?.format().isNotNullOrEmpty()){
//                        leftText.textApp(house.left?.format().toString())
//                    }
//                    if (house.debt?.format().isNotNullOrEmpty()){
//                        debtText.textApp(house.debt?.format().toString())
//                    }
                    //allSum.textApp(house.allSum?.format().toString())
                    textNumber.textApp("№${house.number}")
                    squareText.textApp("${house.area} м²")
                    adminHint.textApp(compositionRoot.mActivity.getString(R.string.rums_count))
                    addressHint.textApp(compositionRoot.mActivity.getString(R.string.floor))
                    adminName.textApp(house.rooms)
                    addressText.textApp(house.floor.toString())

                    var fmt =  SimpleDateFormat("yyyy-MM-dd");
                    var date = fmt.parse(house.created_at)
                    when(house.status){
                        ZERO->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.parseColor("#F95F12"), PorterDuff.Mode.SRC_ATOP)
                            goneView(itemChildFlatBinding, ZERO)
                        }
                        ONE->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
                            goneView(itemChildFlatBinding, ONE)
                        }
                    }
                }
            }catch (e:Exception){
                compositionRoot.errorDialog(e.message.toString(),-1){}
            }
        }
    }

    override fun getItemCount(): Int {
        return listFlat.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
      return Vh(ItemChildFlatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listFlat[position],position)
    }


    fun viewTheme(appTheme: AppTheme,itemChildFlatBinding: ItemChildFlatBinding,itemView:View){
        itemChildFlatBinding.textNumber.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.cardChild.setCardBackgroundColor(appTheme.itemCardColor(itemView.context))
        //admin
        itemChildFlatBinding.adminHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.adminName.setTextColor(appTheme.textColorApp(itemView.context))
        // address
        itemChildFlatBinding.addressHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.addressText.setTextColor(appTheme.textColorApp(itemView.context))
        // purchased
        itemChildFlatBinding.purchasedHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.purchasedText.setTextColor(appTheme.textColorApp(itemView.context))
        // Term
        itemChildFlatBinding.termHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.termText.setTextColor(appTheme.textColorApp(itemView.context))
        // PaidFor
        itemChildFlatBinding.paidForHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.paidForText.setTextColor(appTheme.textColorApp(itemView.context))
        // PaidOut
        itemChildFlatBinding.paidOutHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.paidOutText.setTextColor(appTheme.textColorApp(itemView.context))
        // Left
        itemChildFlatBinding.leftHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.leftText.setTextColor(appTheme.textColorApp(itemView.context))
        // DebtText
        itemChildFlatBinding.debtHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.debtText.setTextColor(appTheme.textColorApp(itemView.context))
        // AllSumm
        itemChildFlatBinding.allSumHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.allSummText.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.allSum.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.allSummHint.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.squareHint.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.squareText.setTextColor(appTheme.textColorApp(itemView.context))
    }

    interface OnItemClickListener{
        fun onItemClick(house: House,position: Int)
        fun onItemClickCall(house: House,position: Int)
        fun onItemClickCallSMS(house: House,position: Int)
    }

    fun goneView(itemChildFlatFragment:ItemChildFlatBinding,position: Int){
        itemChildFlatFragment.apply {
            when(position){
                ZERO->{
                    consData.gone()
                    purchasedHint.textApp(compositionRoot.mActivity.getString(R.string.status))
                    purchasedText.textApp(compositionRoot.mActivity.getString(R.string.not_sold))
                }
                ONE->{
                    consData.visible()
                    layoutCall.gone()
                    purchasedHint.textApp(compositionRoot.mActivity.getString(R.string.status))
                    purchasedText.textApp(compositionRoot.mActivity.getString(R.string.sold_out))
                }
            }
            allSummLinear.gone()
            termLinear.gone()
            paidForLinear.gone()
            paidOutLinear.gone()
            leftLinear.gone()
            debtLinear.gone()
            //adminLinear.gone()
            allSummLinear.gone()

            addressLayout.visible()
        }
    }
}