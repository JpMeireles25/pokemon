package com.example.pokemon.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokemon.BuildConfig
import com.example.pokemon.network.ApiIntegration
import com.example.pokemon.network.buildService
import com.example.pokemon.network.response.pokemonDetailsResponse.PokemonDetailsResponse
import com.example.pokemon.network.response.seeAllPokemonResponse.Results
import com.example.pokemon.utils.ResultsPagingSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Callback

class Repository() {

    private val buildApiIntegration by lazy {
        buildService(ApiIntegration::class.java, BuildConfig.BASE_URL)
    }

    fun getPokemon(pokemonName: String, callback: Callback<PokemonDetailsResponse>) {
        val call = buildApiIntegration.callPokemonAPI(pokemonName);
        try {
            val url = call.request().url.toString()
            println("callx getPokemon url: $url")
            println("callx getPokemon url: $pokemonName")
            call.enqueue(callback)
        } catch (t: Throwable) {
            t.printStackTrace()
            callback.onFailure(call, t)
        }

    }

    fun getAllPokemon() : Flow<PagingData<Results>> {
        return Pager(config = PagingConfig(pageSize = 20, maxSize = 200),
            pagingSourceFactory = { ResultsPagingSource(buildApiIntegration) }).flow
    }

}