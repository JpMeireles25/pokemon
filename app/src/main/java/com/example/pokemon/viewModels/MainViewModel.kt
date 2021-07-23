package com.example.pokemon.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemon.BuildConfig

import com.example.pokemon.network.ApiIntegration
import com.example.pokemon.network.buildService
import com.example.pokemon.network.response.seeAllPokemonResponse.PokemonResponse
import com.example.pokemon.network.response.pokemonDetailsResponse.PokemonDetailsResponse
import com.example.pokemon.network.response.seeAllPokemonResponse.Results
import com.example.pokemon.repository.Repository
import com.example.pokemon.utils.Event
import com.example.pokemon.utils.ResultsPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



open class MainViewModel : ViewModel() {

    private val repo: Repository by lazy {
        Repository()
    }
    private val buildApiIntegration by lazy {
        buildService(ApiIntegration::class.java, BuildConfig.BASE_URL)
    }

    val pokemonData =MutableLiveData<PokemonDetailsResponse>()
    var errorMessageStatus = MutableLiveData<Event<Boolean?>>()

    private var IMAGE_URL_START_INDEX : Int = 39

    fun setSelectedPokemon(pokemonDetails: PokemonDetailsResponse){
        pokemonData.value = pokemonDetails
    }


    open fun setErrorMessageStatus(boolean: Boolean) {
        errorMessageStatus.value = Event(boolean)
    }



    fun getAllPokemon() : Flow<PagingData<Results>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { ResultsPagingSource(buildApiIntegration) }).flow.cachedIn(viewModelScope)
    }

    fun getPokemon(pokemonName: String) {
        val callback = object : Callback<PokemonDetailsResponse>{
            override fun onResponse(
                call: Call<PokemonDetailsResponse>,
                detailsResponse: Response<PokemonDetailsResponse>
            ) {
                detailsResponse.body()?.let { it -> setSelectedPokemon(it) }
                setErrorMessageStatus(detailsResponse.code() == 200)

            }

            override fun onFailure(call: Call<PokemonDetailsResponse>, t: Throwable) {
                println("Error getPokemon VM $t")
            }

        }
        repo.getPokemon(pokemonName, callback)
    }
}