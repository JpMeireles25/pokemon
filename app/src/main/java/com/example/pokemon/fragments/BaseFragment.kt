package com.example.pokemon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pokemon.MainActivity

abstract class BaseFragment : Fragment() {

    abstract val layout: Int

    val baseActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    fun showProgressBar() {
        baseActivity.showProgressBar()
    }

    fun hideProgressBar() {
        baseActivity.hideProgressBar()
    }
}