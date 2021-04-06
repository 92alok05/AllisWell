package com.alliswell.patients

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.data.Patient
import com.alliswell.situation.SituationActivity

class PatientListAdapter : ListAdapter<Patient, PatientListAdapter.PatientViewHolder>(PatientsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        return PatientViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.firstName, current.lastName, current.patientId)
    }

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val firstNameView: TextView = itemView.findViewById(R.id.firstName)
        private val lastNameView: TextView = itemView.findViewById(R.id.lastName)
        private val patientIdView: TextView = itemView.findViewById(R.id.patientId)

        private val patientCard: CardView = itemView.findViewById(R.id.patientCard)

        fun bind(firstName: String?, lastName: String?, patientId: Int) {
            firstNameView.text = firstName
            lastNameView.text = lastName
            patientIdView.text = patientId.toString()

            patientCard.setOnClickListener { view ->
                val sitationActivityForPatient = Intent(view.context, SituationActivity::class.java)
                sitationActivityForPatient.putExtra("patientId", patientId.toString())
                startActivity(view.context, sitationActivityForPatient, null)
            }
        }

        companion object {
            fun create(parent: ViewGroup): PatientViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.patient_list_item, parent, false)
                return PatientViewHolder(view)
            }
        }
    }

    class PatientsComparator : DiffUtil.ItemCallback<Patient>() {
        override fun areItemsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Patient, newItem: Patient): Boolean {
            return oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName
        }
    }
}