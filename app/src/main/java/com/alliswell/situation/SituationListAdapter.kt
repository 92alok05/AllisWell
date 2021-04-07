package com.alliswell.situation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.data.Situation
import com.alliswell.details.DetailsMainActivity
import com.alliswell.patients.R

class SituationListAdapter(patientId: Int) : ListAdapter<Situation, SituationListAdapter.SituationViewHolder>(SituationComparator()) {

    private val _patientId: Int = patientId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SituationViewHolder {
        return SituationViewHolder.create(parent, _patientId)
    }

    override fun onBindViewHolder(holder: SituationViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.description, current.situationId)
    }

    class SituationViewHolder(itemView: View, patientId: Int) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.situationTitle)
        private val descriptionView: TextView = itemView.findViewById(R.id.situationDescription)
        private val situationIdView: TextView = itemView.findViewById(R.id.situationId)

        private val situationCard: CardView = itemView.findViewById(R.id.situationCard)

        private val _patientId: Int = patientId

        fun bind(situationTitle: String?, situationDescription: String?, situationId: Int) {
            titleView.text = situationTitle
            descriptionView.text = situationDescription
            situationIdView.text = situationId.toString()

            situationCard.setOnClickListener { view ->
                val detailsActivity = Intent(view.context, DetailsMainActivity::class.java)
                detailsActivity.putExtra("situationId", situationId.toString())
                detailsActivity.putExtra("patientId", _patientId.toString())
                startActivity(view.context, detailsActivity, null)
            }
        }

        companion object {
            fun create(parent: ViewGroup, patientId: Int): SituationViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.situation_list_item, parent, false)
                return SituationViewHolder(view, patientId)
            }
        }
    }

    class SituationComparator : DiffUtil.ItemCallback<Situation>() {
        override fun areItemsTheSame(oldItem: Situation, newItem: Situation): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Situation, newItem: Situation): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description
        }
    }
}