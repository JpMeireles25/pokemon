package com.example.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.pokemon.utils.sendPostFavouritePokemon
import com.example.pokemon.utils.setImageFromUrl
import com.example.pokemon.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_pokemon_details.*


class PokemonDetailsFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    val baseActivity by lazy {
        activity as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance() =
            PokemonDetailsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
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
        }
    }

    private fun useObservables(){
        viewModel.pokemonData.observe(viewLifecycleOwner,object : Observer<PokemonDetailsResponse>{
            override fun onChanged(pokemonDetails: PokemonDetailsResponse?) {
                if (pokemonDetails != null) {
                    updateUi(pokemonDetails)
                }
            }

        })
    }

    private fun updateUi(pokemonDetails: PokemonDetailsResponse) {
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