package com.example.pokemon

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokemon.network.ApiIntegration
import com.example.pokemon.network.buildService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStream
import java.net.HttpURLConnection



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class NetworkCallTest {
    private var context = InstrumentationRegistry.getInstrumentation().targetContext
    private var mockWebServer = MockWebServer()
    private lateinit var apiService: ApiIntegration

    @Before
    fun setup() {
        mockWebServer.start()
         val buildApiIntegration by lazy {
            buildService(ApiIntegration::class.java, mockWebServer.url("/").toString())
        }
       apiService = buildApiIntegration


        val jsonStream: InputStream = context!!.resources.assets.open("response.json")
        val jsonBytes: ByteArray = jsonStream.readBytes()


        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(String(jsonBytes))
        mockWebServer.enqueue(response)

    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_response_name() = runBlocking {
        val data = apiService.callAllPokemonAPI(0)
        ViewMatchers.assertThat(data.results[0].name, CoreMatchers.equalTo("bulbasaur"))
    }

    @Test
    fun test_id_extraction() = runBlocking {
        val data = apiService.callAllPokemonAPI(1)
        val id = data.results[0].url?.substringAfter("pokemon")?.replace("/", "")?.toInt()
        ViewMatchers.assertThat(id, CoreMatchers.equalTo(1))
    }
}

@RunWith(AndroidJUnit4::class)
class PokemonListFragmentTest {

    @Rule
    @JvmField
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun editText_is_showing() {
        Espresso.onView(withId(R.id.searchEditText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun rv_is_showing() {
        Espresso.onView(withId(R.id.all_pokemon_rv))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
