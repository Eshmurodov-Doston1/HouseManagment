package com.example.housemanagment.adapters.rvAdapter.adapterFlat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.housemanagment.R
import com.example.housemanagment.databinding.ItemFlatBinding
import com.example.housemanagment.models.demoMenu.flat.Flat
import com.example.housemanagment.models.demoMenu.flat.FlatData
import com.example.housemanagment.utils.AppCompositionRoot
import com.example.housemanagment.utils.extension.textApp

class MainAdapterFlat(
    private val listFlatData: ArrayList<FlatData>,
    val onItemClickListener: OnItemClickListener,
    private val compositionRoot: AppCompositionRoot
):RecyclerView.Adapter<MainAdapterFlat.Vh>() {
    inner class Vh(var itemFlatBinding: ItemFlatBinding):RecyclerView.ViewHolder(itemFlatBinding.root){
        fun onBind(flatData: FlatData,position: Int){
            try {
                itemFlatBinding.title.textApp("${itemView.context.getString(R.string.sector)} ${flatData.title}")
                val rvGenericChild = ChildAdapterFlat(flatData.listFlat,this@MainAdapterFlat,compositionRoot)
                itemFlatBinding.rvItem.adapter = rvGenericChild
            }catch (e:Exception){
             compositionRoot.errorDialog(e.message.toString(),-1){}
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemFlatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listFlatData[position],position)
    }

    override fun getItemCount(): Int {
        return listFlatData.size
    }
    interface OnItemClickListener{
        fun onItemClick(flat: Flat,position: Int)
        fun onItemClickCall(flat: Flat,position: Int)
        fun onItemClickCallSMS(flat: Flat,position: Int)
    }
}