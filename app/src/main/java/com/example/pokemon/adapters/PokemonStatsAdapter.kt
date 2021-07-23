package com.example.pokemon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.network.response.pokemonDetailsResponse.Stats
import com.example.pokemon.R

class PokemonStatsAdapter(
    private val statsList: List<Stats>?,
) : RecyclerView.Adapter<PokemonStatsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_stats_adapter_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        statsList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return statsList?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.stats_item_name)
        var valueText: TextView = itemView.findViewById(R.id.stats_item_value)
        fun bind(stat: Stats) {
            nameText.text = stat.stat?.name
            valueText.text = stat.base_stat.toString()
        }
    }
}