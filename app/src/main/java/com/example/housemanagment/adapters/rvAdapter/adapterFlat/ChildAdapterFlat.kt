package com.example.housemanagment.adapters.rvAdapter.adapterFlat

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ablanco.zoomy.Zoomy
import com.example.housemanagment.databinding.ItemChildFlatBinding
import com.example.housemanagment.models.demoMenu.flat.Flat
import com.example.housemanagment.utils.*
import com.example.housemanagment.utils.AppConstant.ONE
import com.example.housemanagment.utils.AppConstant.TWO
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.*


class ChildAdapterFlat(
    private val listFlat:ArrayList<Flat>,
    private val mainAdapterFlat: MainAdapterFlat,
    private val compositionRoot: AppCompositionRoot
):RecyclerView.Adapter<ChildAdapterFlat.Vh>() {
    inner class Vh(var itemChildFlatBinding: ItemChildFlatBinding):RecyclerView.ViewHolder(itemChildFlatBinding.root){
        fun onBind(flat: Flat,position: Int){
            try {
                itemChildFlatBinding.cardChild.setOnClickListener {
                    mainAdapterFlat.onItemClickListener.onItemClick(flat,position)
                }

                itemChildFlatBinding.callBtn.setOnClickListener {
                    mainAdapterFlat.onItemClickListener.onItemClickCall(flat, position)
                }
                itemChildFlatBinding.chatBtn.setOnClickListener {
                    mainAdapterFlat.onItemClickListener.onItemClickCallSMS(flat,position)
                }

                val builder: Zoomy.Builder = Zoomy.Builder(compositionRoot.mActivity)
                    .target(itemChildFlatBinding.image)
                    .enableImmersiveMode(false)
                    .animateZooming(true)
                builder.register()


                itemChildFlatBinding.apply {
                    if (flat.adminFullName.isNotNullOrEmpty()){
                        adminName.textApp(flat.adminFullName.toString())
                    }
                    addressText.textApp(flat.flatAddress)
                    if (flat.status.isNotNullOrEmpty()){
                        purchasedText.textApp(flat.status.toString())
                    }
                    if (flat.term.isNotNullOrEmpty()){
                        termText.textApp(flat.term.toString())
                    }
                    if (flat.paid_for.isNotNullOrEmpty())
                    {
                        paidForText.textApp(flat.paid_for.toString())
                    }
                    if (flat.paidOut?.format().isNotNullOrEmpty())
                    {
                        paidOutText.textApp(flat.paidOut?.format().toString())
                    }
                    if (flat.left?.format().isNotNullOrEmpty()){
                        leftText.textApp(flat.left?.format().toString())
                    }
                    if (flat.debt?.format().isNotNullOrEmpty()){
                        debtText.textApp(flat.debt?.format().toString())
                    }
                    allSum.textApp(flat.allSum?.format().toString())
                    textNumber.textApp("№${flat.number}")
                    compositionRoot.setImage(image, flat.image.toString(),15f)
                    when(flat.statustContract){
                        ZERO->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                        }
                        ONE->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.parseColor("#F95F12"), PorterDuff.Mode.SRC_ATOP)
                            purchasedLinear.gone()
                            termLinear.gone()
                            paidForLinear.gone()
                            paidOutLinear.gone()
                            leftLinear.gone()
                            debtLinear.gone()
                            adminLinear.gone()
                            squareLinear.visible()
                            allSummLinear.visible()
                            squareText.textApp("${flat.squaremetr} м²")
                            consData.gone()
                        }
                        TWO->{
                            radioStatus.buttonDrawable?.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP)
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

}