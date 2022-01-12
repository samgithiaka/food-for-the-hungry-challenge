package com.samgithiaka.apps.foodforthehungry.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.samgithiaka.apps.foodforthehungry.R

class SplashScreenActivity : AppCompatActivity() {
    private val TAG = "SplashScreenActivity"
    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        checkIfSignedIn()

    }

    private fun checkIfSignedIn() {
        try {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                updateUI(currentUser)
            } else {
                scheduleSplashScreen()
            }
        } catch (e: Exception) {
            Log.e(TAG, "onStart: ", e)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            proceedToDashboard()
        }else{
            proceedToSignIn()
            Log.e(TAG, "updateUI: user is null" )
        }
    }

    private fun proceedToDashboard() {
        try{
            finish()
            this.startActivity(Intent(this, MainActivity::class.java))
        }catch (e : Exception) {
            Log.e("TAG", "proceedToDashboard: ",e )
        }
    }

    fun scheduleSplashScreen() {
        val splashDuration = 3000

        // Using handler with postDelayed called runnable run method
        Looper.myLooper()?.let {
            Handler(it).postDelayed(
                { proceedToSignIn() }, splashDuration.toLong()
            )
        } // wait for 3 seconds
    }

    private fun proceedToSignIn() {
        try{
            this.startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }catch (e : Exception) {
            Log.e("TAG", "proceedToSignIn: ",e )
        }
    }
}