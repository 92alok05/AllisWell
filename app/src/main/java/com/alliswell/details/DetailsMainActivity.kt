package com.alliswell.details

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alliswell.common.AllIsWellApplication
import com.alliswell.data.Detail
import com.alliswell.details.ui.journal.AddJournalDetailActivity
import com.alliswell.details.ui.journal.JournalViewModel
import com.alliswell.details.ui.journal.JournalViewModelFactory
import com.alliswell.patients.PatientViewModelFactory
import com.alliswell.patients.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_details_main.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DetailsMainActivity : AppCompatActivity() {

    private val addDetailsActivityRequestCode = 1
    var _patientId: Int = -1
    var _situationId: Int = -1

    private val journalViewModel: JournalViewModel by viewModels {
        JournalViewModelFactory((application as AllIsWellApplication).repository)
    }

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

    fun onClickAddEntry(view: View) {
        val activity2Intent = Intent(this, AddJournalDetailActivity::class.java)
        startActivityForResult(activity2Intent, addDetailsActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == addDetailsActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val paramId = data?.getStringExtra(AddJournalDetailActivity.REPLY_PARAM_ID)
            val paramVaue = data?.getStringExtra(AddJournalDetailActivity.REPLY_PARAM_VALUE)
            val entryDate = data?.getStringExtra(AddJournalDetailActivity.REPLY_DATE)

            val parser =  SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = parser.parse(entryDate!!)


            val detail = Detail(date!!, paramId!!.toInt(), paramVaue!!.toFloat(), _patientId, _situationId)
            journalViewModel.insert(detail)

            Snackbar.make(nav_view,  R.string.success_saved, Snackbar.LENGTH_LONG)
                .show()
        } else {
            val contextView = findViewById<View>(R.id.patientsRecycler)
            Snackbar.make(contextView,  R.string.empty_not_saved, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}