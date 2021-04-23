package com.alliswell.details.ui.journal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alliswell.common.AllIsWellApplication
import com.alliswell.data.Detail
import com.alliswell.details.DetailsMainActivity
import com.alliswell.patients.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_journal.*
import java.text.SimpleDateFormat
import java.util.*

class JournalFragment : Fragment() {

    val addDetailsActivityRequestCode = 1

    private val journalViewModel: JournalViewModel by viewModels {
        JournalViewModelFactory((activity?.application as AllIsWellApplication).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_journal, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = JournalListAdapter()
        journalRecycler.adapter = adapter
        journalRecycler.layoutManager = LinearLayoutManager(context)

        val patientId = (activity as DetailsMainActivity).getPatientId()
        val situationId = (activity as DetailsMainActivity).getSituationId()

        journalViewModel.getDetailsForPatientAndSituation(patientId, situationId)
            .observe( viewLifecycleOwner, androidx.lifecycle.Observer { details ->
                details?.let { adapter.submitList(details) }
            } )

        btnAddEntry.setOnClickListener { view ->
            val activity2Intent = Intent(activity, AddJournalDetailActivity::class.java)
            activity2Intent.putExtra("patientId", patientId.toString())
            activity2Intent.putExtra("situationId", situationId.toString())
            startActivityForResult(activity2Intent, addDetailsActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val paramId = data?.getStringExtra(AddJournalDetailActivity.REPLY_PARAM_ID)
            val paramVaue = data?.getStringExtra(AddJournalDetailActivity.REPLY_PARAM_VALUE)
            val entryDate = data?.getStringExtra(AddJournalDetailActivity.REPLY_DATE)

            val parser =  SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = parser.parse(entryDate!!)


            val patientId = (activity as DetailsMainActivity).getPatientId()
            val situationId = (activity as DetailsMainActivity).getSituationId()

            val detail = Detail(date!!, paramId!!.toInt(), paramVaue!!.toFloat(), patientId,situationId )
            journalViewModel.insert(detail)

            Snackbar.make(journalRecycler,  R.string.success_saved, Snackbar.LENGTH_LONG)
                .show()
        } else {
            Snackbar.make(journalRecycler,  R.string.empty_not_saved, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}