package com.eazuapps.findmeapp.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.eazuapps.findmeapp.R
import com.eazuapps.findmeapp.core.base.BaseFragment
import com.eazuapps.findmeapp.core.utils.Resource
import com.eazuapps.findmeapp.core.utils.enable
import com.eazuapps.findmeapp.core.utils.snackBar
import com.eazuapps.findmeapp.core.utils.visible
import com.eazuapps.findmeapp.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvAlreadyHav.setOnClickListener {
            Log.d(TAG, "loginNow clicked")
            findNavController().navigate(R.id.action_navigation_register_to_navigation_login)
        }
        binding.progressbar.visible(false)
        binding.buttonRegister.enable(false)

        lifecycleScope.launch {
            viewModel.signupFlow.collect {
                binding.progressbar.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        Log.d(TAG, "register success called")
                        findNavController().navigate(R.id.action_navigation_register_to_navigation_home)
                    }
                    is Resource.Failure -> {
                        Log.d(TAG, "failed to register with ${it.exception.printStackTrace()}")
                        it.exception.message?.let { it1 -> requireActivity().snackBar(it1) }
                    }
                    Resource.Loading -> binding.progressbar.visible(true)
                    else -> {}
                }
            }
        }

        binding.inputConfirmPassword.addTextChangedListener {
            val confirmPassword = binding.inputPassword.text.toString().trim()
            binding.buttonRegister.enable(confirmPassword == it.toString())
        }

        binding.buttonRegister.setOnClickListener {
            Log.d(TAG, "register called")
            viewModel.signup(
                binding.inputName.text.toString().trim(),
                binding.inputEmail.text.toString().trim(),
                binding.inputPassword.text.toString().trim()
            )
//            findNavController().navigate(R.id.action_navigation_register_to_navigation_login)
        }
    }
}