package com.alliswell.situation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.data.Situation
import com.alliswell.patients.R

class SituationListAdapter : ListAdapter<Situation, SituationListAdapter.SituationViewHolder>(SituationComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SituationViewHolder {
        return SituationListAdapter.SituationViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SituationViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.description, current.situationId)
    }

    class SituationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.situationTitle)
        private val descriptionView: TextView = itemView.findViewById(R.id.situationDescription)
        private val situationIdView: TextView = itemView.findViewById(R.id.situationId)

        private val situationCard: CardView = itemView.findViewById(R.id.situationCard)

        fun bind(situationTitle: String?, situationDescription: String?, situationId: Int) {
            titleView.text = situationTitle
            descriptionView.text = situationDescription
            situationIdView.text = situationId.toString()

            situationCard.setOnClickListener { view -> }
        }

        companion object {
            fun create(parent: ViewGroup): SituationViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.situation_list_item, parent, false)
                return SituationViewHolder(view)
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