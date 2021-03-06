package com.example.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.MainActivity
import com.example.pokemon.R
import com.example.pokemon.adapters.PokemonAbilitiesAdapter
import com.example.pokemon.adapters.PokemonStatsAdapter
import com.example.pokemon.network.request.FavRequest
import com.example.pokemon.network.response.pokemonDetailsResponse.Abilities
import com.example.pokemon.network.response.pokemonDetailsResponse.PokemonDetailsResponse
import com.example.pokemon.network.response.pokemonDetailsResponse.Stats
import com.example.pokemon.utils.Event
import com.example.pokemon.utils.sendPostFavouritePokemon
import com.example.pokemon.utils.setImageFromUrl
import com.example.pokemon.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_pokemon_details.*


class PokemonDetailsFragment : BaseFragment() {
    private lateinit var viewModel: MainViewModel
    override val layout = R.layout.fragment_pokemon_details

    companion object {
        fun newInstance() =
            PokemonDetailsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(baseActivity).get(MainViewModel::class.java)
        useObservables()

    }

    private fun setOnClickListener(pokemonDetails: PokemonDetailsResponse) {
        val favRequest = FavRequest(name = pokemonDetails.name)
        mark_favourite.setOnClickListener {
            context?.sendPostFavouritePokemon(favRequest)
            Toast.makeText(context, "${pokemonDetails.name} marked has favourite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun useObservables(){
        viewModel.getSelectedPokemon().observe(viewLifecycleOwner,object : Observer<Event<PokemonDetailsResponse>>{
            override fun onChanged(pokemonDetails: Event<PokemonDetailsResponse>?) {
                if (pokemonDetails != null) {
                val poke = pokemonDetails.getContentIfNotHandled()
                    poke?.let { updateUi(it) }
                }
            }
        })
    }

    private fun updateUi(pokemonDetails: PokemonDetailsResponse) {
        hideProgressBar()
        details_scroll_view.visibility = View.VISIBLE
        pokemon_name.text = pokemonDetails.name

        val weight = getString(R.string.pokemon_weight,pokemonDetails.weight)
        pokemon_weight.text = weight

        val height = getString(R.string.pokemon_height,pokemonDetails.height)
        pokemon_height.text = height

        val order = getString(R.string.pokemon_order,pokemonDetails.order)
        pokemon_order.text = order

        val experience = getString(R.string.pokemon_base_experience,pokemonDetails.base_experience)
        pokemon_base_experience.text = experience

        pokemonDetails.sprites.front_default?.let { pokemon_image.setImageFromUrl(it) }

        setOnClickListener(pokemonDetails)
        setStatsRv(pokemonDetails.stats)
        setAbilitiesRv(pokemonDetails.abilities)
    }

    private fun setStatsRv(stats: List<Stats>?) {
        val adapter = PokemonStatsAdapter(stats)
        pokemon_stats_rv.layoutManager  = LinearLayoutManager(context)
        pokemon_stats_rv.adapter = adapter
    }
    private fun setAbilitiesRv(abilities: List<Abilities>?) {
        val adapter = PokemonAbilitiesAdapter(abilities)
        pokemon_abilities_rv.layoutManager  = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false  )
        pokemon_abilities_rv.adapter = adapter
    }

}