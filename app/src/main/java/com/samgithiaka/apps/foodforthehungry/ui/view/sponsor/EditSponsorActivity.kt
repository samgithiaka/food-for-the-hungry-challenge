package com.samgithiaka.apps.foodforthehungry.ui.view.sponsor

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.data.model.Action
import com.samgithiaka.apps.foodforthehungry.data.repository.SponsorsRepository
import com.samgithiaka.apps.foodforthehungry.data.room.dbs.SponsorsDatabase
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveSponsorRoomModel
import com.samgithiaka.apps.foodforthehungry.databinding.ActivityEditSponsorBinding
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseActivity
import com.samgithiaka.apps.foodforthehungry.ui.base.SponsorsModelFactory
import com.samgithiaka.apps.foodforthehungry.ui.viewmodel.SponsorDetailsViewModel

class EditSponsorActivity : BaseActivity() {

    private lateinit var viewModel: SponsorDetailsViewModel
    private lateinit var binding : ActivityEditSponsorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = activityContext?.let { DataBindingUtil.setContentView(it,R.layout.activity_edit_sponsor) }!!
        setUpInits()
    }

    private fun setUpInits() {
        try{
            val dao = activityContext?.let { SponsorsDatabase.getInstance(it.application)?.sponsorStorageDao() }
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

            val i = intent

            val sponsor: SaveSponsorRoomModel? = i.getSerializableExtra("sponsorObject") as SaveSponsorRoomModel?
            viewModel.inputFullName.value = sponsor?.fullName
            viewModel.inputPassportNumber.value = sponsor?.passportNumber
            viewModel.inputPhoneNumber.value = sponsor?.phoneNumber
            viewModel.inputLocation.value = sponsor?.location
            binding.viewModel = viewModel

            if (sponsor != null) {
                viewModel.initUpdateAndDelete(sponsor)
            }
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
            Log.e("TAG", "setUpInits: ", e)
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