package com.example.pokemon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.network.response.pokemonDetailsResponse.Abilities

class PokemonAbilitiesAdapter(
    private val abilitiesList: List<Abilities>?,
) : RecyclerView.Adapter<PokemonAbilitiesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_abilities_adapter_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        abilitiesList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return abilitiesList?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.abilites_item_name)

        fun bind(ability: Abilities) {
            nameText.text = ability.ability.name
        }
    }
}