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
import com.eazuapps.findmeapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "LoginFragment"

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "called onCreate")
        binding.progressbar.visible(false)
        binding.btnLogin.enable(false)

        lifecycleScope.launch {
            viewModel.loginFlow.collect {
                binding.progressbar.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        findNavController().navigate(R.id.action_navigation_login_to_navigation_home)
                        Log.d(TAG, "success login called")
                    }
                    is Resource.Failure -> {
                        it.exception.message?.let { it1 -> requireActivity().snackBar(it1) }
                    }
                    Resource.Loading -> binding.progressbar.visible(true)
                    else -> {}
                }
            }
        }

        binding.inputPassword.addTextChangedListener {
            val email = binding.inputEmail.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.btnLogin.setOnClickListener {
            Log.d(TAG, "login clicked")
            login()
        }

        binding.tvDontHav.setOnClickListener {
            Log.d(TAG, "registerNow clicked")
            findNavController().navigate(R.id.action_navigation_login_to_navigation_register)
        }
    }

    private fun login() {
        val email = binding.inputEmail.text.toString().trim()
        val password = binding.inputPassword.text.toString().trim()
        viewModel.login(email, password)
    }
}