package com.example.pokemon.network

import com.example.pokemon.network.request.FavRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FavIntegration {
        @POST("6f261184-484f-40e6-a35f-26716dcd3fd0")
        fun event(@Body request: FavRequest): Call<Void>
}