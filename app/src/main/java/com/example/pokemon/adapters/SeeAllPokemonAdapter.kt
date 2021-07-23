package com.example.pokemon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.R
import com.example.pokemon.network.response.seeAllPokemonResponse.Results
import com.example.pokemon.utils.getPicUrl
import com.example.pokemon.utils.setImageFromUrl


class SeeAllPokemonAdapter(private val clickListener: SeeAllPokemonListener?): PagingDataAdapter< Results, SeeAllPokemonAdapter.ViewHolder>(DiffUtilCallback()){
    override fun onBindViewHolder(holder: SeeAllPokemonAdapter.ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
    interface SeeAllPokemonListener{
        fun onClick(name: String)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeeAllPokemonAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.see_all_pokemon_adapter_item, parent, false)
        )
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameText: TextView = itemView.findViewById(R.id.see_all_name)
        var image: ImageView = itemView.findViewById(R.id.see_all_image)
        var linerLayout: LinearLayout = itemView.findViewById(R.id.seeAll_linerLayout)

        fun bind(data: Results) {
            val pictureUrl = data.url?.getPicUrl()
            nameText.text = data.name
            pictureUrl?.let { image.setImageFromUrl(it) }
            linerLayout.setOnClickListener {
                data.name?.let { it1 -> clickListener?.onClick(it1) }
            }

        }
    }
    class DiffUtilCallback: DiffUtil.ItemCallback<Results>(){
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
           return  oldItem.name == newItem.name
        }

    }

}