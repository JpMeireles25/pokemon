package com.example.pokemon.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.R
import com.example.pokemon.adapters.SeeAllPokemonAdapter
import com.example.pokemon.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_pokemon_see_all.*
import kotlinx.coroutines.flow.collectLatest


class PokemonSeeAllFragment :BaseFragment() {
    private lateinit var viewModel: MainViewModel
    lateinit var adapter: SeeAllPokemonAdapter
    override val layout = R.layout.fragment_pokemon_see_all

    companion object {
        fun newInstance() =
            PokemonSeeAllFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(baseActivity).get(MainViewModel::class.java)
        showProgressBar()
        setStatsRv()
        initViewModel()
    }

    private fun initViewModel() {

        lifecycleScope.launchWhenCreated {
            hideProgressBar()
            viewModel.getAllPokemon().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setStatsRv() {
        adapter = SeeAllPokemonAdapter(object : SeeAllPokemonAdapter.SeeAllPokemonListener {
            override fun onClick(name: String) {
                showProgressBar()
                viewModel.getPokemon(name, false)
                baseActivity.replaceFragmentStackHistory(
                    PokemonDetailsFragment.newInstance(),
                    "detailsFragment"
                )
            }

        })
        all_pokemon_rv.layoutManager = LinearLayoutManager(context)
        all_pokemon_rv.adapter = adapter
    }

}