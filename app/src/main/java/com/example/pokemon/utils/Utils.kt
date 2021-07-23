package com.example.pokemon.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokemon.network.FavIntegration
import com.example.pokemon.network.buildService
import com.example.pokemon.network.request.FavRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun ImageView.setImageFromUrl(url: String) = try {
    Glide.with(this).load(url).into(this)
} catch (e: Exception) {
    e.printStackTrace()
}

public inline fun CharSequence.substring(startIndex: Int, endIndex: Int = length): String = subSequence(startIndex, endIndex).toString()

// Event wraper class for mutableLiveData
class Event<T>(content: T?) {
    private val mContent: T
    private var hasBeenHandled = false
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }
    }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }

    init {
        requireNotNull(content) { "null values in Event are not allowed." }
        mContent = content
    }
}
fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.getPicUrl(): String {
    val id = this.extractId()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
}


fun Context.sendPostFavouritePokemon(favRequest: FavRequest){
    val buildService = buildService(FavIntegration::class.java, "https://webhook.site/")
    //  println("reportEvent eventRequest $eventName ${Gson().toJson(eventRequest)}")
    buildService.event( favRequest).enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            println("sendPostFavouritePokemon $favRequest onSuccess $response")
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            println("sendPostFavouritePokemon $favRequest onFailure $t")
        }
    })
}