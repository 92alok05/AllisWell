package com.alliswell.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alliswell.common.AllIsWellApplication
import com.alliswell.details.ui.journal.AddJournalDetailActivity
import com.alliswell.details.ui.journal.JournalViewModel
import com.alliswell.details.ui.journal.JournalViewModelFactory
import com.alliswell.patients.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailsMainActivity : AppCompatActivity() {


    var _patientId: Int = -1
    var _situationId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_journal, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val patientIdStr = intent.getStringExtra("patientId")
        _patientId = Integer.parseInt(patientIdStr!!)

        val situationIdStr = intent.getStringExtra("situationId")
        _situationId = Integer.parseInt(situationIdStr!!)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    public fun getPatientId(): Int {
        return _patientId
    }

    public fun getSituationId(): Int {
        return _situationId
    }


}