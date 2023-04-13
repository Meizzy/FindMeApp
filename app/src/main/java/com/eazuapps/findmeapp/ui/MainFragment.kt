package com.eazuapps.findmeapp.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eazuapps.findmeapp.R
import com.eazuapps.findmeapp.core.base.BaseFragment
import com.eazuapps.findmeapp.databinding.FragmentMainBinding

class MainFragment: BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val navController =
        requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)

        super.onViewCreated(view, savedInstanceState)
    }

}