package com.alliswell.situation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.common.AllIsWellApplication
import com.alliswell.data.Parameter
import com.alliswell.data.PatientSituation
import com.alliswell.data.Situation
import com.alliswell.data.SituationParameter
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
        val patientIdStr = intent.getStringExtra("patientId")
        val patientId = Integer.parseInt(patientIdStr!!)

        val adapter = SituationListAdapter(patientId)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



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
            // Get the information about title and description from intent
            val title = data?.getStringExtra(AddSituationActivity.REPLY_TITLE)
            val description = data?.getStringExtra(AddSituationActivity.REPLY_DESCRIPTION)
            val patientId = data?.getStringExtra(AddSituationActivity.REPLY_PATIENTID)

            val situation = Situation(title, description)

            // Since insert returns the id of the situation in the form of live data, we have to observe the
            // result
            situationViewModel.insert(situation).observe(this, Observer { situationId ->
                val patientSituation = PatientSituation(Integer.parseInt(patientId!!) , situationId.toInt() )
                situationViewModel.insert(patientSituation)

                // Insert parameter and its associated situation similarly
                val parameters = getParameters()
                for (parameter in parameters) {
                    situationViewModel.insert(parameter).observe(this, Observer { parameterId ->
                        val situationParameter = SituationParameter(situationId.toInt(), parameterId.toInt())
                        situationViewModel.insert(situationParameter)

                        val contextView = findViewById<View>(R.id.situationRecycler)
                        Snackbar.make(contextView,  R.string.success_saved, Snackbar.LENGTH_LONG)
                                .show()
                    })
                }

            })

        } else {
            val contextView = findViewById<View>(R.id.situationRecycler)
            Snackbar.make(contextView,  R.string.empty_not_saved, Snackbar.LENGTH_LONG)
                .show()
        }
    }

    fun getParameters(): List<Parameter> {
        // Get all the fields from the added text view
        // If the text is blank then ignore the addition
        val parameters = mutableListOf<Parameter>()
        for(parameterText in AddSituationActivity.parameterTexts) {
            val paramName = parameterText.text.toString()
            if (!paramName.isBlank()) {
                val parameter = Parameter(paramName)
                parameters.add(parameter)
            }
        }
        return parameters
    }
}