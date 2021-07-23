package com.example.pokemon.repository

import com.example.pokemon.BuildConfig
import com.example.pokemon.network.ApiIntegration
import com.example.pokemon.network.buildService
import com.example.pokemon.network.response.seeAllPokemonResponse.PokemonResponse
import com.example.pokemon.network.response.pokemonDetailsResponse.PokemonDetailsResponse
import retrofit2.Callback

class Repository() {

    private val buildApiIntegration by lazy {
        buildService(ApiIntegration::class.java, BuildConfig.BASE_URL)
    }



    fun getPokemon(pokemonName: String, callback: Callback<PokemonDetailsResponse>) {
        val call = buildApiIntegration.callPokemonAPI(pokemonName);
        try {
            val url = call.request().url().toString()
            println("callx getPokemon url: $url")
            println("callx getPokemon url: $pokemonName")
            call.enqueue(callback)
        } catch (t: Throwable) {
            t.printStackTrace()
            callback.onFailure(call, t)
        }

    }

   /* fun getAllPokemon(offset: Int, callback: Callback<PokemonResponse>) {
        val call = buildApiIntegration.callAllPokemonAPI(offset);
        try {
            val url = call.request().url().toString()
            println("callx getPokemon url: $url")
            println("callx getPokemon url: $offset")
            call.enqueue(callback)
        } catch (t: Throwable) {
            t.printStackTrace()
            callback.onFailure(call, t)
        }

    }*/

}