package com.alliswell.patients


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.common.AllIsWellApplication
import com.alliswell.data.Patient
import com.alliswell.situation.SituationActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class PatientsActivity : AppCompatActivity() {

    private val addPatientsActivityRequestCode = 1

    private val patientViewModel: PatientViewModel by viewModels {
        PatientViewModelFactory((application as AllIsWellApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients)
        setSupportActionBar(findViewById(R.id.toolbar))

        val recyclerView = findViewById<RecyclerView>(R.id.patientsRecycler)
        val adapter = PatientListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        patientViewModel.patients.observe(this, Observer { patients ->
            // Update the cached copy of the words in the adapter.
            patients?.let { adapter.submitList(patients) }
        })

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val activity2Intent = Intent(this, AddPatientsActivity::class.java)
            startActivityForResult(activity2Intent, addPatientsActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == addPatientsActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val firstName = data?.getStringExtra(AddPatientsActivity.REPLY_FIRST_NAME)
            val lastName = data?.getStringExtra(AddPatientsActivity.REPLY_LAST_NAME)
            val patient = Patient(firstName, lastName)
            patientViewModel.insert(patient)
            val contextView = findViewById<View>(R.id.patientsRecycler)
            Snackbar.make(contextView,  R.string.success_saved, Snackbar.LENGTH_LONG)
                .show()
        } else {
            val contextView = findViewById<View>(R.id.patientsRecycler)
            Snackbar.make(contextView,  R.string.empty_not_saved, Snackbar.LENGTH_LONG)
                .show()
        }
    }

}