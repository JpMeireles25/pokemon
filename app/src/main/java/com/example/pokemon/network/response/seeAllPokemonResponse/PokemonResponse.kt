package com.example.pokemon.network.response.seeAllPokemonResponse


data class PokemonResponse(

    val count : Int?,
    val next : String?,
    val previous: String,
    val results: List<Results>
)