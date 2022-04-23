package com.d2k.task.ui.dashboard

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.d2k.task.R
import com.d2k.task.databinding.ActivityDasboardBinding
import com.d2k.tmb.base.BaseActivity


class DashboardActivity : BaseActivity() {
    lateinit var activityDasboardBinding: ActivityDasboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDasboardBinding= ActivityDasboardBinding.inflate(layoutInflater)
        setContentView(activityDasboardBinding.root)
/*        val navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)
        val bottomNavigationView = activityDasboardBinding.bnDashboard
        NavigationUI.setupWithNavController(bottomNavigationView, navController)*/
    }
}