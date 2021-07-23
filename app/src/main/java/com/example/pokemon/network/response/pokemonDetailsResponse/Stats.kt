package com.example.pokemon.network.response.pokemonDetailsResponse

import androidx.annotation.Keep

@Keep
data class Stats (
    val base_stat: Int?,
    val stat: Stat?
)