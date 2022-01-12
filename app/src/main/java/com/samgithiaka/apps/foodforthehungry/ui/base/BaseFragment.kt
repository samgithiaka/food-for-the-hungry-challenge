package com.samgithiaka.apps.foodforthehungry.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        activityContext = this.activity as BaseActivity
    }

    companion object {
        lateinit var activityContext : BaseActivity
    }
}