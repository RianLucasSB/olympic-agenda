package com.boas.rian.olympicagenda.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boas.rian.olympicagenda.databinding.EventItemBinding
import com.boas.rian.olympicagenda.extensions.load
import com.boas.rian.olympicagenda.extensions.toFormattedString
import com.boas.rian.olympicagenda.model.Event

class EventsAdapter(
    private val context: Context, events: List<Event> = emptyList()
) : RecyclerView.Adapter<EventsAdapter.EventsAdapterViewHolder>() {

    private val events = events.toMutableList()

    inner class EventsAdapterViewHolder(val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            if (event.competitors.size > 2) {
                binding.eventItemTwoCompetitorsContainer.visibility = View.GONE
                binding.eventItemSeeAllCompetitors.visibility = View.VISIBLE
            } else {
                binding.eventItemFirstCompetitorName.text =
                    event.competitors[0].name.split(" ").take(2).joinToString(" ")
                binding.eventItemSecondCompetitorName.text =
                    event.competitors[1].name.split(" ").take(2).joinToString(" ")
                binding.eventItemFirstCompetitorImage.load(event.competitors[0].countryFlag)
                binding.eventItemSecondCompetitorImage.load(event.competitors[1].countryFlag)
            }
            binding.eventItemDisciplineText.text = event.disciplineName
            binding.eventItemGenderText.text = event.genderCode
            binding.eventItemDay.text = event.startDate?.toFormattedString()
            binding.eventItemTime.text =
                "${event.startDate?.hour}:${if (event.startDate?.minute == 0) "00" else event.startDate?.minute}"
            binding.eventItemDisciplineImage.load(event.disciplineImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapterViewHolder {
        return EventsAdapterViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsAdapterViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun update(eventsList: List<Event>) {
        notifyItemRangeRemoved(0, events.size)
        this.events.clear()
        this.events.addAll(eventsList)
        notifyItemInserted(this.events.size)
    }

    fun add(eventsList: List<Event>) {
        this.events.addAll(eventsList)
        notifyItemInserted(this.events.size)
    }
}