package com.example.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon.MainActivity
import com.example.pokemon.R
import com.example.pokemon.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    val baseActivity by lazy {
        activity as MainActivity
    }

    companion object {
        fun newInstance() =
            SearchFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)

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

    fun setListener(){
        searchEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val pokemonName = searchEditText.text.toString()
                viewModel.getPokemon(pokemonName)
                searchEditText.text.clear()
                return@OnEditorActionListener true
            }
            false
        })

        see_all_btn.setOnClickListener {
            baseActivity.replaceFragmentStackHistory(PokemonSeeAllFragment.newInstance(),"seeAllFragment")
        }

    }

    private fun handleObservables() {
        viewModel.errorMessageStatus.observe(viewLifecycleOwner, {
            if (it != null) {
                val shouldSave = it.getContentIfNotHandled()
                val noDetailsFragment = baseActivity.noDetailsFragment()
                if (noDetailsFragment) {
                    if (shouldSave != null && shouldSave == true) {
                        val myFragment = PokemonDetailsFragment.newInstance();
                        baseActivity.replaceFragmentStackHistory(myFragment, "detailsFragment")
                    } else if (shouldSave != null) {
                        Toast.makeText(context, "No Pokemon found", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }

}

