package com.samgithiaka.apps.foodforthehungry.ui.view

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.databinding.ActivityMainBinding
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseActivity
import com.samgithiaka.apps.foodforthehungry.ui.view.fragments.main.HomeFragment
import com.samgithiaka.apps.foodforthehungry.ui.view.fragments.main.ProfileFragment
import com.samgithiaka.apps.foodforthehungry.ui.view.fragments.main.SponsorsFragment
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class MainActivity : BaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var fragment : Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = activityContext?.let { DataBindingUtil.setContentView(it,R.layout.activity_main) }!!
        setupFragments()
    }

    private fun setupFragments() {
        try{
            val menuItems = arrayOf(
                CbnMenuItem(
                    R.drawable.ic_baseline_home_24, // the icon
                    R.drawable.avd_baseline_home_24, // the AVD that will be shown in FAB
                    R.id.navigation_home // optional if you use Jetpack Navigation
                ),
                CbnMenuItem(
                    R.drawable.ic_sponsor,
                    R.drawable.avd_sponsor,
                    R.id.navigation_sponsor
                ),
                CbnMenuItem(
                    R.drawable.ic_user_profile,
                    R.drawable.avd_user_profile,
                    R.id.navigation_profile
                )
            )
            binding.navView.setMenuItems(menuItems, 0)

            binding.navView.setOnMenuItemClickListener { cbnMenuItem, index ->
            when (cbnMenuItem.destinationId) {
                    R.id.navigation_home -> {
                        fragment = HomeFragment()
                        loadSelectedFragment(fragment as HomeFragment)
                        return@setOnMenuItemClickListener
                    }
                    R.id.navigation_sponsor -> {

                        fragment = SponsorsFragment()
                        loadSelectedFragment(fragment as SponsorsFragment)
                        return@setOnMenuItemClickListener
                    }
                    R.id.navigation_profile -> {

                        fragment = ProfileFragment()
                        loadSelectedFragment(fragment as ProfileFragment)
                        return@setOnMenuItemClickListener
                    }

                }

            }
        }catch (e : Exception) {
            Log.e(TAG, "setupFragments: ",e )
        }
    }

    override fun onResume() {
        try {
            super.onResume()
            loadSelectedFragment((if (fragment != null) fragment else HomeFragment())!!)
        } catch (e: Exception) {
            Log.e(TAG, "onResume: ", e)
        }
    }

    private fun loadSelectedFragment(fragment: Fragment) {
        try {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_container, fragment)
            transaction.commit()
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "loadSelectedFragment: ", e)
        }
    }

}