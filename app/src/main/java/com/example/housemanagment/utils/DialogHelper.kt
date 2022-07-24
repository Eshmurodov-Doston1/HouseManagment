package com.example.housemanagment.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.housemanagment.R
import com.example.housemanagment.adapters.rvAdapter.chatAdapter.ChatAdapter
import com.example.housemanagment.databinding.ChatDialogBinding
import com.example.housemanagment.databinding.DialogOtherBinding
import com.example.housemanagment.databinding.ErrorDialogBinding
import com.example.housemanagment.models.demoMenu.chat.ChatData
import com.example.housemanagment.presentation.activitys.AuthActivity
import com.example.housemanagment.uiTheme.AppTheme
import com.example.housemanagment.utils.AppConstant.CLIENT_CODE_END
import com.example.housemanagment.utils.AppConstant.CLIENT_CODE_START
import com.example.housemanagment.utils.AppConstant.ERROR_NO_INTERNET
import com.example.housemanagment.utils.AppConstant.SERVER_CODE_END
import com.example.housemanagment.utils.AppConstant.SERVER_CODE_START
import com.example.housemanagment.utils.AppConstant.UN_AUTHORIZATION
import com.example.housemanagment.utils.AppConstant.ZERO
import com.example.housemanagment.utils.extension.gone
import com.example.housemanagment.utils.extension.isNotNullOrEmpty
import com.example.housemanagment.utils.extension.textApp
import com.example.housemanagment.utils.sharedPreference.MySharedPreferences

class DialogHelper(
    private val context:Context,
    private val inflater: LayoutInflater,
    private val activity:AppCompatActivity
) {

    private lateinit var chatAdapter: ChatAdapter

    fun errorDialog(
        listException: List<String>,
        code:Int,
        mySharedPreferences: MySharedPreferences?,
        click:(click:Boolean)->Unit
    ){
        var dialog = AlertDialog.Builder(context, R.style.BottomSheetDialogThem)
        val create = dialog.create()
        val errorDialog = ErrorDialogBinding.inflate(inflater)
       if (mySharedPreferences?.theme == true){
           errorDialog.cardDialog.setCardBackgroundColor(ContextCompat.getColor(context,R.color.backgroundDarkColor_Tool))
           errorDialog.title.setTextColor(Color.WHITE)
       }else{
           errorDialog.cardDialog.setCardBackgroundColor(ContextCompat.getColor(context, R.color.backgroundColor))
           errorDialog.title.setTextColor(R.color.textColor)
       }
        create.setView(errorDialog.root)
        when(code){
            -1->{
                var errorMessage = ""
                listException.onEach {
                    errorMessage+="$it\n"
                }
                errorDialog.title.text = errorMessage
                errorDialog.okBtn.gone()
                errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                errorDialog.closeBtn.text = context.getString(R.string.again)
                errorDialog.closeBtn.setOnClickListener {
                    click.invoke(true)
                    create.dismiss()
                }
            }
            ERROR_NO_INTERNET->{
                errorDialog.lottie.setAnimation(R.raw.no_intenet)
                errorDialog.title.textApp(context.getString(R.string.no_internet_connection))
                errorDialog.okBtn.gone()
                errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                errorDialog.closeBtn.text = context.getString(R.string.again)
                errorDialog.closeBtn.setOnClickListener {
                    click.invoke(true)
                    create.dismiss()
                }
            }
            in CLIENT_CODE_START..CLIENT_CODE_END->{
               if (code!= UN_AUTHORIZATION){
                   var errorMessage = ""
                   listException.onEach {
                       errorMessage+="$it\n"
                   }
                   errorDialog.title.textApp(errorMessage)
                   errorDialog.okBtn.gone()
                   errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                   errorDialog.closeBtn.text = context.getString(R.string.again)
                   errorDialog.closeBtn.setOnClickListener {
                       click.invoke(true)
                       create.dismiss()
                   }
               }else{
                   activity.startActivity(Intent(context,AuthActivity::class.java))
                   mySharedPreferences?.clear()
                   activity.finish()
               }
            }
            in SERVER_CODE_START..SERVER_CODE_END->{
                errorDialog.title.textApp(context.getString(R.string.server_error))
                errorDialog.okBtn.gone()
                errorDialog.closeBtn.setPadding(ZERO,ZERO,ZERO,ZERO)
                errorDialog.closeBtn.text = context.getString(R.string.again)
                errorDialog.closeBtn.setOnClickListener {
                    click.invoke(true)
                    create.dismiss()
                }
            }
        }
        create.show()
    }



    fun messageDialog(
        nameAdmin:String,
        list: List<ChatData>,
        onCLick:(message:String)->Unit
    ){
        var alertDialog = AlertDialog.Builder(context)
        val create = alertDialog.create()
        val chatBinding = ChatDialogBinding.inflate(LayoutInflater.from(context), null, false)
        create.setView(chatBinding.root)
        chatAdapter = ChatAdapter(0)
        chatAdapter.submitList(list)
        chatBinding.rvChat.adapter = chatAdapter
        chatBinding.cardSend.setOnClickListener {
            val message = chatBinding.chatMessage.text.toString().trim()
            if (message.isNotNullOrEmpty()){
                onCLick.invoke(message)
            }
        }
        chatBinding.textBottom.textApp(nameAdmin)
        create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        create.show()
    }




    fun otherDialog(
        position:Int,
        appTheme: AppTheme,
        onCLick: (isClicked:Boolean) -> Unit
    ){
        var alertDialog = AlertDialog.Builder(context,R.style.BottomSheetDialogThem)
        val create = alertDialog.create()
        val dialogOtherBinding = DialogOtherBinding.inflate(inflater)
        dialogOtherBinding.textOther.setTextColor(appTheme.textColorApp(context))
        val gradientDrawable = dialogOtherBinding.consDialog.background as GradientDrawable
        gradientDrawable.setColor(appTheme.backgroundColorApp(context))
        create.setView(dialogOtherBinding.root)
        dialogOtherBinding.apply {
            cancelButton.setOnClickListener {
                onCLick.invoke(false)
                create.dismiss()
            }

            okBtn.setOnClickListener {
                onCLick.invoke(true)
                create.dismiss()
            }
        }
        create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        create.show()
    }








}