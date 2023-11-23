package com.android.project.scouthub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.project.scouthub.db.UserDatabase
import com.android.project.scouthub.repository.UsersRepository
import com.android.project.scouthub.viewModel.UsersViewModel
import com.android.project.scouthub.viewModel.viewModelFactory.UsersViewModelProviderFactory

class ScoutHubActivity : AppCompatActivity() {

    lateinit var viewModel: UsersViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scout_hub)

        val usersRepository = UsersRepository(UserDatabase(this))
        val viewModelProviderFactory = UsersViewModelProviderFactory(usersRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(UsersViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.scoutNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}