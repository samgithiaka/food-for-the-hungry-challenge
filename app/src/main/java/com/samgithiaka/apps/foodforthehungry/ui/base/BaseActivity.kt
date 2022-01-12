package com.samgithiaka.apps.foodforthehungry.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        activityContext = this
    }

    companion object {
        private const val TAG = "SafBaseActivity"
        var activityContext: BaseActivity? = null
    }

}