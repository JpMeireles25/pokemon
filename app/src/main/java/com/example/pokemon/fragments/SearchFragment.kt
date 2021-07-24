package com.example.pokemon.fragments

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.R
import com.example.pokemon.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : BaseFragment() {
    private lateinit var viewModel: MainViewModel
    override val layout = R.layout.fragment_search
    companion object {
        fun newInstance() =
            SearchFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(baseActivity).get(MainViewModel::class.java)
        handleObservables()
    }


    override fun onResume() {
        super.onResume()
        setListener()
    }

    private fun setListener(){
        searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val pokemonName = searchEditText.text.toString()
                viewModel.getPokemon(pokemonName, true)
                searchEditText.text.clear()
                showProgressBar()
                return@OnEditorActionListener true
            }
            false
        })

        see_all_btn.setOnClickListener {
            baseActivity.replaceFragmentStackHistory(PokemonSeeAllFragment.newInstance(),"seeAllFragment")
        }

    }

    private fun handleObservables() {
        viewModel.getErrorMessageValue().observe(viewLifecycleOwner, {
            if (it != null) {
                val shouldSave = it.getContentIfNotHandled()
                    if (shouldSave != null && shouldSave == true) {
                        val myFragment = PokemonDetailsFragment.newInstance();
                        baseActivity.replaceFragmentStackHistory(myFragment, "detailsFragment")
                        hideProgressBar()
                    } else if (shouldSave != null) {
                        hideProgressBar()
                        Toast.makeText(context, "No Pokemon found", Toast.LENGTH_SHORT).show()
                    }
            }

        })

    }

}

