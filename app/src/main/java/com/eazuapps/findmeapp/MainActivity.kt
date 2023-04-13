package com.eazuapps.findmeapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.eazuapps.findmeapp.databinding.ActivityMainBinding
import com.eazuapps.findmeapp.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isStarting = false //true
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_auth) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.starting_navigation)
        isStarting = viewModel.currentUser != null
        if (isStarting) graph.setStartDestination(R.id.navigation_home)
        else graph.setStartDestination(R.id.navigation_splash_screen)

        val navController = navHostFragment.navController
        navController.setGraph(graph, intent.extras)
    }
}