package com.example.housemanagment.adapters.rvAdapter.adapterFlat

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.housemanagment.R
import com.example.housemanagment.databinding.ItemChildFlatBinding
import com.example.housemanagment.models.bookingData.Booking
import com.example.housemanagment.models.house.House
import com.example.housemanagment.uiTheme.AppTheme
import com.example.housemanagment.utils.*
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.*
import java.util.*


class ChildAdapterFlat(
    private val listFlat:ArrayList<House>,
    private val onItemClickListener: OnItemClickListener,
    private val compositionRoot: AppCompositionRoot,
    private val appTheme: AppTheme,
    private val docPos:Int?=0
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
                itemChildFlatBinding.apply {
                    if(docPos==1){
                        price.gone()
                        textNumber.textApp("${house.build} ${house.blok} №${house.number}")
                    }else{
                        textNumber.textApp("№${house.number}")
                    }
                    squareText.textApp("${house.area} м²")
                    adminHint.textApp(compositionRoot.mActivity.getString(R.string.rums_count))
                    addressHint.textApp(compositionRoot.mActivity.getString(R.string.floor))
                    adminName.textApp(house.rooms)
                    addressText.textApp(house.floor.toString())
                    var houseSumma:Double?=0.0
                    if (house.summa!=null) houseSumma = house.summa
                    allSum.textApp(houseSumma?.format().toString())
                    paidOutText.textApp(house.paid?.format().toString())
                    squareSummaText.textApp(house.price.format())
                    
//                    var fmt =  SimpleDateFormat("yyyy-MM-dd")
//                    var date = fmt.parse(house.created_at)

                    if (house.status!= TWO && house.status!= ONE){
                        booking.visible()
                        price.visible()
                    }

                    booking.setOnClickListener {
                        onItemClickListener.onItemClickListenerBooking(listFlat[position],position,false)
                    }

                    price.setOnClickListener {
                        onItemClickListener.onItemClickListenerBooking(listFlat[position],position,true)
                    }

                    when(house.status){
                        ZERO->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.parseColor("#F95F12"), PorterDuff.Mode.SRC_ATOP)
                            goneView(itemChildFlatBinding, ZERO)
                        }
                        ONE->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
                            goneView(itemChildFlatBinding, ONE)
                        }
                        TWO->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP)
                            goneView(itemChildFlatBinding, TWO)
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
        // PaidOut
        itemChildFlatBinding.paidOutHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.paidOutText.setTextColor(appTheme.textColorApp(itemView.context))
        // AllSumm
        itemChildFlatBinding.allSumHint.setTextColor(appTheme.hintColor(itemView.context))
        itemChildFlatBinding.allSummText.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.allSum.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.allSummHint.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.squareHint.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.squareText.setTextColor(appTheme.textColorApp(itemView.context))

        itemChildFlatBinding.squareSummaText.setTextColor(appTheme.textColorApp(itemView.context))
        itemChildFlatBinding.squareSummaHint.setTextColor(appTheme.hintColor(itemView.context))
    }

    interface OnItemClickListener{
        fun onItemClick(house: House,position: Int)
        fun onItemClickCall(house: House,position: Int)
        fun onItemClickListenerBooking(house: House,position: Int,isPrice:Boolean)
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
                    purchasedHint.textApp(compositionRoot.mActivity.getString(R.string.status))
                    purchasedText.textApp(compositionRoot.mActivity.getString(R.string.sold_out))
                }
                TWO->{
                    purchasedHint.textApp(compositionRoot.mActivity.getString(R.string.status))
                    purchasedText.textApp(compositionRoot.mActivity.getString(R.string.booking_home))
                }
            }
            allSummLinear.gone()
            paidOutLinear.gone()
            allSummLinear.gone()

            addressLayout.visible()
        }
    }
}