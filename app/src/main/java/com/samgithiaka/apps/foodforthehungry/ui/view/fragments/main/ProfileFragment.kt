package com.samgithiaka.apps.foodforthehungry.ui.view.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.databinding.FragmentProfileBinding
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseFragment

class ProfileFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false)
        binding.btnLogout.setOnClickListener { logOutUser() }
        binding.txtUserName.text = Firebase.auth.currentUser?.displayName
        binding.txtUserEmail.text = Firebase.auth.currentUser?.email
        return binding.root
    }

    private fun logOutUser() {
        try{
            Firebase.auth.signOut()
            activity?.finish()
        }catch (e : Exception) {
            Log.e(TAG, "logOutUser: ",e )
        }
    }
    
    companion object {
        private const val TAG = "ProfileFragment"
        lateinit var binding: FragmentProfileBinding
    }
}