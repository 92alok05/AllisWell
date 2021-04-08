package com.alliswell.details.ui.journal;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alliswell.data.Detail
import com.alliswell.patients.R
import java.text.SimpleDateFormat
import java.util.*

class JournalListAdapter : ListAdapter<Detail, JournalListAdapter.JournalViewHolder>(
    JournalListAdapter.JournalComparator()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JournalViewHolder {
        return JournalViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(
            current.date,
            current.parameterId,
            current.parameterValue,
            current.patientId,
            current.situationId)
    }

    class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val detailDateView: TextView = itemView.findViewById(R.id.detailDate)
        private val parameterIdView: TextView = itemView.findViewById(R.id.parameterId)
        private val parameterValueView: TextView = itemView.findViewById(R.id.parameterValue)

        fun bind(date: Date, parameterId: Int, parameterValue: Float, patientId: Int, situationId: Int) {

            val parser =  SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
            val dateStr = parser.format(date)

            detailDateView.text = dateStr
            parameterIdView.text = parameterId.toString()
            parameterValueView.text = parameterValue.toString()
        }

        companion object {
            fun create(parent: ViewGroup): JournalViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.journal_list_item, parent, false)
                return JournalViewHolder(view)
            }
        }

    }

    class JournalComparator : DiffUtil.ItemCallback<Detail>() {
        override fun areItemsTheSame(oldItem: Detail, newItem: Detail): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Detail, newItem: Detail): Boolean {
            return oldItem.date == newItem.date &&
                    oldItem.parameterId == newItem.parameterId &&
                    oldItem.parameterValue == newItem.parameterValue &&
                    oldItem.patientId == newItem.patientId &&
                    oldItem.situationId == newItem.situationId
        }

    }

}