package com.samgithiaka.apps.foodforthehungry.ui.view.sponsor

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.data.model.Action
import com.samgithiaka.apps.foodforthehungry.data.repository.SponsorsRepository
import com.samgithiaka.apps.foodforthehungry.data.room.dbs.SponsorsDatabase
import com.samgithiaka.apps.foodforthehungry.databinding.ActivityAddSponsorBinding
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseActivity
import com.samgithiaka.apps.foodforthehungry.ui.base.SponsorsModelFactory
import com.samgithiaka.apps.foodforthehungry.ui.viewmodel.SponsorDetailsViewModel

class AddSponsorActivity : BaseActivity() {
    private val TAG = "AddSponsorActivity"
    private lateinit var viewModel: SponsorDetailsViewModel
    private lateinit var binding : ActivityAddSponsorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = activityContext?.let { DataBindingUtil.setContentView(it,R.layout.activity_add_sponsor) }!!
        setUpInits()
    }

    private fun setUpInits() {
        try{
            val dao = SponsorsDatabase.getInstance(application)?.sponsorStorageDao()
            val repository = dao?.let { SponsorsRepository(it) }
            val factory = repository?.let { SponsorsModelFactory(it) }
            viewModel = factory?.let {
                activityContext?.let { it1 ->
                    ViewModelProvider(
                        it1,
                        it
                    )
                }?.get(SponsorDetailsViewModel::class.java)
            }!!
            binding.viewModel = viewModel
            binding.lifecycleOwner = activityContext

            activityContext?.let {
                viewModel.getAction().observe(
                    it,
                    { action ->
                        if (action != null) {
                            handleAction(action)
                        }
                    })
            }
        }catch (e : Exception){
            Log.e(TAG, "setUpInits: ", e)
        }
    }

    private fun handleAction(action: Action) {
        when (action.getValue()) {
            Action.SHOW_SPONSOR -> {
                finish()
            }
        }
    }
}