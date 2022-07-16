package com.example.housemanagment.adapters.rvAdapter.child

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.housemanagment.R
import com.example.housemanagment.databinding.ItemCashChildBinding
import com.example.housemanagment.databinding.ItemMainChildBinding
import com.example.housemanagment.models.demoMenu.Item
import com.example.housemanagment.models.demoMenu.cash.CashItemData
import com.example.housemanagment.utils.extension.format
import com.example.housemanagment.utils.extension.textApp

class RvGenericChild<T:Any>(
    private val listItem:ArrayList<T>,
    @LayoutRes private val layoutRes: Int,
):RecyclerView.Adapter<GenericViewHolderChild<T>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolderChild<T> {
        var item = LayoutInflater.from(parent.context).inflate(layoutRes,parent,false)

        return GenericViewHolderChild(item)
    }

    override fun onBindViewHolder(holder: GenericViewHolderChild<T>, position: Int) {
        holder.onBind(listItem[position],position,layoutRes)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}
open class GenericViewHolderChild<T>(itemView: View):RecyclerView.ViewHolder(itemView),HolderChild<T>{
    override fun onBind(data: T, position: Int, layoutRes: Int) {
        when(layoutRes){
            R.layout.item_main_child->{
                val itemMainChildBinding = ItemMainChildBinding.bind(itemView)
                val item = data as Item
               itemMainChildBinding.location.textApp(item.location)
               itemMainChildBinding.kvo.textApp(item.kvo.format())
               itemMainChildBinding.summ.textApp(item.summ.format())
            }
            R.layout.item_cash_child->{
                val itemCashChildBinding = ItemCashChildBinding.bind(itemView)
                val cashItemData = data as CashItemData
                itemCashChildBinding.imageCash.setImageResource(cashItemData.icon)
                itemCashChildBinding.textPay.textApp(cashItemData.paymentStr)
                itemCashChildBinding.summ.textApp(cashItemData.payment.format())
            }
        }
    }
}