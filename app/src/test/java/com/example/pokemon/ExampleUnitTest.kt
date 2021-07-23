package com.example.pokemon

import com.example.pokemon.network.ApiIntegration
import com.example.pokemon.network.buildService
import com.example.pokemon.viewModels.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class CreditTest {
    val mockWebServer = MockWebServer()

    val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiIntegration::class.java)

    val myApi =   buildService(ApiIntegration::class.java, BuildConfig.BASE_URL)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
    @Test
    fun `can get pokemon`() {

        //mockWebServer.enqueue("discover-movies-200.json", 200)
      /*  val mockedVieModel: MainViewModel = Mockito.mock(MainViewModel::class.java)


        mockedVieModel.getPokemon("abra")
        mockedVieModel.pokemonData.observeForever {
            assertNotNull(it)
            assertTrue(it.name == "abra")
        }*/



    }

}