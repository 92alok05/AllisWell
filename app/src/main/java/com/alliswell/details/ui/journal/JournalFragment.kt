package com.alliswell.details.ui.journal

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alliswell.patients.AddPatientsActivity
import com.alliswell.patients.R
import kotlinx.android.synthetic.main.fragment_journal.*

class JournalFragment : Fragment() {

    private lateinit var journalViewModel: JournalViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        journalViewModel =
                ViewModelProvider(this).get(JournalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_journal, container, false)

        return root
    }
}