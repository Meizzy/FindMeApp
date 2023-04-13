package com.eazuapps.findmeapp.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.eazuapps.findmeapp.R
import com.eazuapps.findmeapp.core.base.BaseFragment
import com.eazuapps.findmeapp.databinding.FragmentSettingsBinding
import com.eazuapps.findmeapp.ui.auth.AuthViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI(viewModel.currentUser)

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            activity?.findNavController(R.id.nav_host_fragment_activity_auth)
                ?.navigate(R.id.action_navigation_home_to_navigation_login)
        }

    }

    private fun updateUI(user: FirebaseUser?) {
        with(binding) {
            tvNameSettings.text = getString(
                R.string.name_settings,user?.displayName ?: "")
            tvEmailSettings.text = getString(R.string.email,user?.email ?: "")
        }
    }

}