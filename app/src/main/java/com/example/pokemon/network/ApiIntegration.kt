package com.example.pokemon.network

import com.example.pokemon.network.response.seeAllPokemonResponse.PokemonResponse
import com.example.pokemon.network.response.pokemonDetailsResponse.PokemonDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiIntegration {
    @GET("pokemon/{name}")
    fun callPokemonAPI(@Path("name") name: String): Call<PokemonDetailsResponse>

    @GET("pokemon")
   suspend fun callAllPokemonAPI(@Query("offset") offset: Int): PokemonResponse
}