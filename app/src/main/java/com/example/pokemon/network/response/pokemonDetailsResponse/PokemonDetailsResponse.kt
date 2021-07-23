package com.example.pokemon.network.response.pokemonDetailsResponse

import androidx.annotation.Keep

@Keep
data class PokemonDetailsResponse (
    val abilities: List<Abilities>?,
    val base_experience: Int?,
    val forms: List<Form>?,
    val id: Long?,
    val order: Int?,
    val name: String?,
    val height: Int?,
    val weight: Int?,
    val moves: List<Moves>?,
    val stats: List<Stats>?,
    val sprites: Sprite

)
