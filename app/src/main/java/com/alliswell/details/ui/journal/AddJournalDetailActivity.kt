package com.alliswell.details.ui.journal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alliswell.common.AllIsWellApplication
import com.alliswell.data.Parameter
import com.alliswell.patients.R
import kotlinx.android.synthetic.main.activity_add_journal_detail.*
import kotlinx.android.synthetic.main.situation_list_item.*


class AddJournalDetailActivity : AppCompatActivity() {

    private val journalViewModel: JournalViewModel by viewModels {
        JournalViewModelFactory((application as AllIsWellApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_journal_detail)

        val patientIdStr = intent.getStringExtra("patientId")
        val situationIdStr = intent.getStringExtra("situationId")


        val arrayList: ArrayList<Parameter> = ArrayList()

        journalViewModel.getParamsForSituation(situationIdStr!!.toInt()).observe(this, Observer { t ->
            t.first().parameters.forEach { p -> arrayList.add(p) }
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayList)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerParamNames.adapter = arrayAdapter
        })

        spinnerParamNames?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val i=1
            }

        }

        btnDetailsSave.setOnClickListener{
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTextDate.text) ||
                TextUtils.isEmpty(editTextParamId.text) ||
                TextUtils.isEmpty(editTextTextParamValue.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(REPLY_PARAM_ID, editTextParamId.text.toString());
                replyIntent.putExtra(REPLY_PARAM_VALUE, editTextTextParamValue.text.toString());
                replyIntent.putExtra(REPLY_DATE, editTextDate.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val REPLY_PARAM_ID = "com.alliswell.details.paramname"
        const val REPLY_PARAM_VALUE = "com.alliswell.details.paramvalue"
        const val REPLY_DATE = "com.alliswell.details.detaildate"
    }
}