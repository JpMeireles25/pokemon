package com.example.pokemon.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemon.BuildConfig

import com.example.pokemon.network.ApiIntegration
import com.example.pokemon.network.buildService
import com.example.pokemon.network.response.pokemonDetailsResponse.PokemonDetailsResponse
import com.example.pokemon.network.response.seeAllPokemonResponse.Results
import com.example.pokemon.repository.Repository
import com.example.pokemon.utils.Event
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



open class MainViewModel : ViewModel() {

    private val repo: Repository by lazy {
        Repository()
    }

    private val pokemonData = MutableLiveData<Event<PokemonDetailsResponse>>()
    private var errorMessageStatus = MutableLiveData<Event<Boolean?>>()

    fun setSelectedPokemon(pokemonDetails: PokemonDetailsResponse){
        pokemonData.value = Event(pokemonDetails)
    }

    fun setErrorMessageStatus(boolean: Boolean) {
        errorMessageStatus.value = Event(boolean)
    }

    fun getSelectedPokemon() : MutableLiveData<Event<PokemonDetailsResponse>>{
        return pokemonData
    }
    fun getErrorMessageValue(): MutableLiveData<Event<Boolean?>>{
        return errorMessageStatus
    }

    fun getAllPokemon(): Flow<PagingData<Results>> {
        return repo.getAllPokemon().cachedIn(viewModelScope)
    }

    fun getPokemon(pokemonName: String, shouldGetCallStatus: Boolean) {
        val callback = object : Callback<PokemonDetailsResponse>{
            override fun onResponse(
                call: Call<PokemonDetailsResponse>,
                detailsResponse: Response<PokemonDetailsResponse>
            ) {
                detailsResponse.body()?.let { it -> setSelectedPokemon(it) }
                if(shouldGetCallStatus){
                setErrorMessageStatus(detailsResponse.code() == 200)
                }

            }
            override fun onFailure(call: Call<PokemonDetailsResponse>, t: Throwable) {
                println("Error getPokemon VM $t")
            }

        }
        repo.getPokemon(pokemonName, callback)
    }
}