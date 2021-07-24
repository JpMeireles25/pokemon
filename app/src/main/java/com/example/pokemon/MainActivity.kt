package com.example.pokemon

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pokemon.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var backEnable = true
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

    fun showProgressBar() {
        backEnable = false
        progress_bar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        backEnable = true
        progress_bar.visibility = View.GONE
    }


    override fun onBackPressed() = try {
        if (!backEnable) {
            println("onBackPressed base !backEnable")
        } else {
            val count = supportFragmentManager.backStackEntryCount

            if (count == 0) {
                super.onBackPressed()
            } else {
                supportFragmentManager.popBackStack()
            }
        }
    } catch (e: Exception) {
        super.onBackPressed()
    }
}