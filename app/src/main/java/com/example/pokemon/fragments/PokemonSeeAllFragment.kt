package com.example.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemon.MainActivity
import com.example.pokemon.R
import com.example.pokemon.adapters.SeeAllPokemonAdapter
import com.example.pokemon.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_pokemon_see_all.*
import kotlinx.coroutines.flow.collectLatest


class PokemonSeeAllFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    lateinit var adapter : SeeAllPokemonAdapter
    val baseActivity by lazy {
        activity as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object {
        fun newInstance() =
            PokemonSeeAllFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_see_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(baseActivity).get(MainViewModel::class.java)
        useObservables()
        setStatsRv()
        initViewModel()
    }

    private fun useObservables(){

    }

    private fun initViewModel(){
        lifecycleScope.launchWhenCreated {
            viewModel.getAllPokemon().collectLatest {
                adapter.submitData(it)
            }
        }
    }
    private fun setStatsRv() {
        adapter = SeeAllPokemonAdapter(object : SeeAllPokemonAdapter.SeeAllPokemonListener{
            override fun onClick(name: String) {
                viewModel.getPokemon(name)
                baseActivity.replaceFragmentStackHistory(PokemonDetailsFragment.newInstance(),"detailsFragment")
            }

        })
        all_pokemon_rv.layoutManager  = LinearLayoutManager(context)
        all_pokemon_rv.adapter = adapter
    }

}