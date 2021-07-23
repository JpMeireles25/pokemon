package com.example.pokemon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pokemon.fragments.SearchFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragmentWithStackHistory(SearchFragment.newInstance(),"searchFragment", R.id.activity_content)
    }

    fun addFragmentWithStackHistory(
        nextFragment: Fragment,
        tag: String? = null,
        frameLayoutId: Int?
    ) =
        frameLayoutId?.let {
            supportFragmentManager.beginTransaction()
                .add(it, nextFragment, tag)
                .addToBackStack(tag)
                .commit()
        }


     fun replaceFragmentStackHistory(
        nextFragment: Fragment,
        tag: String? = null,
    ) = try {
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_content, nextFragment, tag)
                .addToBackStack(tag)
                .commit()

    } catch (e: Exception) {
        e.printStackTrace()
        onBackPressed()
    }

    fun noDetailsFragment(): Boolean{
        val detailsFragment = supportFragmentManager.findFragmentByTag("detailsFragment")
        return detailsFragment != null
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}