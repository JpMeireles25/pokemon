package com.example.pokemon.network.response.pokemonDetailsResponse

import androidx.annotation.Keep

@Keep
data class Abilities (
   val ability: Ability,
   val is_hidden: Boolean,
   val slot: Int
)