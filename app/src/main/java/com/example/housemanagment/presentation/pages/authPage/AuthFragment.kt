package com.example.housemanagment.presentation.pages.authPage


import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.dolatkia.animatedThemeManager.AppTheme
import com.example.housemanagment.R
import com.example.housemanagment.database.entity.userEntity.UserEntity
import com.example.housemanagment.databinding.FragmentAuthBinding
import com.example.housemanagment.models.auth.auhtReq.AuthReq
import com.example.housemanagment.presentation.activitys.AuthActivity
import com.example.housemanagment.presentation.activitys.MainActivity
import com.example.housemanagment.presentation.pages.base.BasePage
import com.example.housemanagment.utils.AppConstant.PASSWORD_LENGTH
import com.example.housemanagment.utils.extension.*
import com.example.housemanagment.vm.AuthViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : BasePage(R.layout.fragment_auth) {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        bindingView = binding
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            password.doAfterTextChanged { str ->
                if (str?.toString()?.trim()?.length!! >= PASSWORD_LENGTH) {
                    val gradientDrawable = GradientDrawable()
                    gradientDrawable.shape = GradientDrawable.RECTANGLE
                    gradientDrawable.cornerRadius = 20f
                    gradientDrawable.setColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.buttonFull
                        )
                    )
                    loginBtn.background = gradientDrawable
                    loginBtn.enabledTrue()
                } else {
                    loginBtn.setBackgroundResource(R.drawable.button_back)
                    loginBtn.enabledFalse()
                }
            }

            loginBtn.setOnClickListener {
                val login = login.text.toString().trim()
                val password = password.text.toString().trim()
                if (!login.isNullOrEmpty()) {
                    errorDialog(requireActivity().getString(R.string.not_available_login))
                } else if (!password.isNullOrEmpty()) {
                    errorDialog(requireActivity().getString(R.string.not_available_password))
                } else if (!password.isLength()) {
                    errorDialog(requireActivity().getString(R.string.password_length))
                } else {
                    authViewModel.authData(AuthReq(login,password))
                    launch {
                        authViewModel.authData.fetchResult(appCompositionRootAuth.uiControllerApp) { authResponse->
                            launch {
                                authResponse?.user.apply {
                                    authViewModel.saveUserEntity(UserEntity(
                                        this?.created_at ?: "",
                                        this?.email ?: "",
                                        this?.email_verified_at.toString(), this?.id ?: 0,
                                        this?.name.toString(), this?.role_as.toString(), this?.updated_at.toString()
                                    ))
                                }
                            }
                            authViewModel.sharedPreferences.token = authResponse?.token
                            appCompositionRootAuth.mActivity.startNewActivity(MainActivity::class.java)
                            appCompositionRootAuth.mActivity.finish()
                        }
                    }
                }
            }
        }
    }


    fun errorDialog(str: String) {
        appCompositionRootAuth.errorDialog(str, -1,authViewModel.sharedPreferences) {}
    }

}

