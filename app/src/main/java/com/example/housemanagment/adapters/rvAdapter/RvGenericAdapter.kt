package com.example.housemanagment.adapters.rvAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.housemanagment.R
import com.example.housemanagment.databinding.*
import com.example.housemanagment.models.blockData.Block
import com.example.housemanagment.models.buildingData.Building
import com.example.housemanagment.models.demoMenu.DemoItem
import com.example.housemanagment.models.demoMenu.DemoMenu
import com.example.housemanagment.models.demoMenu.cash.CashItem
import com.example.housemanagment.models.demoMenu.employe.Employee
import com.example.housemanagment.models.flat.Flat
import com.example.housemanagment.uiTheme.AppTheme
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.THREE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.format
import com.example.housemanagment.utils.extension.textApp

class RvGenericAdapter<T:Any>(
    private val onItemClickListener: OnItemClickListener<T>,
   @LayoutRes private val layoutRes: Int,
    var currentList:ArrayList<T>,
    private val context:Context,
    private val appTheme: AppTheme?=null,
    private val typePlease:Int?=null,
    private val onClick:(T:Any)->Unit
):RecyclerView.Adapter<GenericViewHolder<T>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        var item = LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
        return GenericViewHolder(item)
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        holder.onBind(currentList[position],position,layoutRes,onItemClickListener,context,appTheme,typePlease,onClick)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    interface OnItemClickListener<T>{
        fun onItemClick(demoMenu: T, position: Int,@LayoutRes layoutRes: Int)
    }
    
    fun filterData(list: ArrayList<T>){
        currentList = list
        notifyDataSetChanged()
    }


}

open class GenericViewHolder<T>(itemView:View):RecyclerView.ViewHolder(itemView),Holder<T>{
    fun loadAnimation(context: Context):Animation{
      return AnimationUtils.loadAnimation(context,R.anim.anim_rv)
    }

    override fun onBind(
        data: T,
        position: Int,
        layoutRes: Int,
        onItemClickListener: RvGenericAdapter.OnItemClickListener<T>,
        context: Context,
        appTheme: AppTheme?,
        typePlease: Int?,
        onClick: (T: Any) -> Unit
    ) {
        when(layoutRes){

            R.layout.item_house->{
                itemView.animation = loadAnimation(context)
                var itemPlaceBinding = ItemPlaceBinding.bind(itemView)
                val placeData = data as Flat
                itemPlaceBinding.name.textApp(placeData.name)
                itemPlaceBinding.quantity.textApp("${context.getString(R.string.quantity)} ${placeData.home_count}")
                itemPlaceBinding.summ.textApp("${context.getString(R.string.pay)} ${placeData.summa.toDouble().format()}")
                itemPlaceBinding.card.setOnClickListener {
                    onItemClickListener.onItemClick(data,position,layoutRes)
                }
                if (appTheme!=null){
                    itemPlaceBinding.name.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.quantity.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.summ.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.card.setCardBackgroundColor(appTheme.itemCardColor(context))
                }
                itemPlaceBinding.textBlock.textApp(context.getString(R.string.flats))
            }

            R.layout.item_home_menu->{
                itemView.animation = loadAnimation(context)
                var itemHomeMenuBinding = ItemHomeMenuBinding.bind(itemView)
                val demoMenu = data as DemoMenu
                itemHomeMenuBinding.icon.setImageResource(demoMenu.icon)
                itemHomeMenuBinding.createTv.textApp(demoMenu.title)
                itemHomeMenuBinding.card.setCardBackgroundColor(demoMenu.colorCard)
                itemHomeMenuBinding.card.setOnClickListener {
                    onItemClickListener.onItemClick(data,position,layoutRes)
                }
            }

            R.layout.item_info->{
                itemView.animation = loadAnimation(context)
                var itemInfoBinding = ItemInfoBinding.bind(itemView)
                val demoItem = data as DemoItem
                itemInfoBinding.title.textApp(demoItem.title)
                itemInfoBinding.quantity.textApp("${context.getString(R.string.quantity)} ${demoItem.quantity}")
                itemInfoBinding.textPay.textApp("${context.getString(R.string.pay)} ${demoItem.pay.format()}")
                itemView.setOnClickListener {
                    onItemClickListener.onItemClick(data,position,layoutRes)
                }
            }
            R.layout.item_cash->{
                val cashItemBinding = ItemCashBinding.bind(itemView)
                val cashItem = data as CashItem
                cashItemBinding.summ.textApp("${cashItem.sum.format()} ${context.getString(R.string.money_type)}")
                cashItemBinding.textData.textApp(cashItem.date)
                cashItemBinding.fullName.textApp(cashItem.fullName)
                when(cashItem.status){
                    ZERO->{
                        cashItemBinding.imgCard.setImageResource(R.drawable.ic_cash_icon)
                        cashItemBinding.imgCard.setBackgroundColor(context.getColor(R.color.image_cash_color))
                    }
                    ONE->{
                        cashItemBinding.imgCard.setImageResource(R.drawable.ic_card_icon)
                        cashItemBinding.imgCard.setBackgroundColor(context.getColor(R.color.image_card_color))
                    }
                    TWO->{
                        cashItemBinding.imgCard.setImageResource(R.drawable.ic_shared_pay_icon)
                        cashItemBinding.imgCard.setBackgroundResource(R.drawable.card_and_cash)
                    }
                }
                if (appTheme!=null){
                    cashItemBinding.cardCash.setCardBackgroundColor(appTheme.itemCardColor(context))
                    cashItemBinding.textData.setTextColor(appTheme.textColorApp(context))
                    cashItemBinding.summ.setTextColor(appTheme.textColorApp(context))
                    cashItemBinding.fullName.setTextColor(appTheme.textColorApp(context))
                }
            }

            R.layout.item_place->{
                var itemPlaceBinding = ItemPlaceBinding.bind(itemView)
                val placeData = data as Building
                itemPlaceBinding.name.textApp(placeData.name)
                itemPlaceBinding.quantity.textApp("${context.getString(R.string.quantity)} ${placeData.blok_count}")
                itemPlaceBinding.summ.textApp("${context.getString(R.string.pay)} ${placeData.summa.toDouble().format()}")
                itemPlaceBinding.card.setOnClickListener {
                    onItemClickListener.onItemClick(data,position,layoutRes)
                }

                if (appTheme!=null){
                    itemPlaceBinding.name.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.quantity.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.summ.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.card.setCardBackgroundColor(appTheme.itemCardColor(context))
                }

                if (typePlease!=null){
                    when(typePlease){
                        ZERO->{
                            itemPlaceBinding.textBlock.textApp(context.getString(R.string.category))
                        }
                    }
                }

            }
            R.layout.item_place_company->{
                itemView.animation = loadAnimation(context)
                var itemPlaceBinding = ItemPlaceBinding.bind(itemView)
                val placeData = data as Block
                itemPlaceBinding.name.textApp(placeData.name)
                itemPlaceBinding.quantity.textApp("${context.getString(R.string.quantity)} ${placeData.dom_count}")
                itemPlaceBinding.summ.textApp("${context.getString(R.string.pay)} ${placeData.summa.toDouble().format()}")
                itemPlaceBinding.card.setOnClickListener {
                    onItemClickListener.onItemClick(data,position,layoutRes)
                }
                itemPlaceBinding.textBlock.textApp(context.getString(R.string.house_cat))

                if (appTheme!=null){
                    itemPlaceBinding.name.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.quantity.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.summ.setTextColor(appTheme.textColorApp(context))
                    itemPlaceBinding.card.setCardBackgroundColor(appTheme.itemCardColor(context))
                }
            }

            R.layout.employe_item->{
                itemView.animation = loadAnimation(context)
                val employeeItemBinding = EmployeItemBinding.bind(itemView)
                val employee = data as Employee
                employeeItemBinding.name.textApp("${context.getString(R.string.full_name)} - ${employee.name}")
                employeeItemBinding.role.textApp("${context.getString(R.string.role)} - ${employee.role}")
                employeeItemBinding.lastEntrance.textApp("${context.getString(R.string.last_entrance)} - ${employee.date}")
                employeeItemBinding.call.setOnClickListener {
                    onClick.invoke(data)
                }
                if (appTheme!=null){
                    employeeItemBinding.apply {
                        card.setCardBackgroundColor(appTheme.itemCardColor(itemView.context))
                        name.setTextColor(appTheme.textColorApp(itemView.context))
                        role.setTextColor(appTheme.textColorApp(itemView.context))
                        lastEntrance.setTextColor(appTheme.textColorApp(itemView.context))
                    }
                }
            }
        }
    }

}