package com.alliswell.situation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.common.AllIsWellApplication
import com.alliswell.data.Patient
import com.alliswell.data.PatientSituation
import com.alliswell.data.Situation
import com.alliswell.patients.AddPatientsActivity
import com.alliswell.patients.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class SituationActivity : AppCompatActivity() {

    private val addSituationActivityRequestCode = 1

    private val situationViewModel: SituationViewModel by viewModels {
        SituationViewModelFactory((application as AllIsWellApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_situations)
        setSupportActionBar(findViewById(R.id.toolbar))

        val recyclerView = findViewById<RecyclerView>(R.id.situationRecycler)
        val adapter = SituationListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val patientIdStr = intent.getStringExtra("patientId")
        val patientId = Integer.parseInt(patientIdStr!!)
        situationViewModel.getSituationsForPatient(patientId).observe(this, Observer { patientWithSituations ->
            // Update the cached copy of the words in the adapter.
            patientWithSituations?.let { adapter.submitList(patientWithSituations.first().situations) }
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val activity2Intent = Intent(this, AddSituationActivity::class.java)
            activity2Intent.putExtra("patientId", patientIdStr)
            startActivityForResult(activity2Intent, addSituationActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == addSituationActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(AddSituationActivity.REPLY_TITLE)
            val description = data?.getStringExtra(AddSituationActivity.REPLY_DESCRIPTION)
            val patientId = data?.getStringExtra(AddSituationActivity.REPLY_PATIENTID)
            val situation = Situation(title, description)
            situationViewModel.insert(situation).observe(this, Observer { t ->
                val patientSituation = PatientSituation(Integer.parseInt(patientId) , t.toInt() )
                situationViewModel.insert(patientSituation)
            })

        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }
}