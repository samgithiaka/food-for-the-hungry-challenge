package com.samgithiaka.apps.foodforthehungry.ui.view.child

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.samgithiaka.apps.foodforthehungry.R
import com.samgithiaka.apps.foodforthehungry.data.model.Action
import com.samgithiaka.apps.foodforthehungry.data.repository.ChildRepository
import com.samgithiaka.apps.foodforthehungry.data.room.dbs.ChildDatabase
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveChildRoomModel
import com.samgithiaka.apps.foodforthehungry.databinding.ActivityChildProfileBinding
import com.samgithiaka.apps.foodforthehungry.ui.base.BaseActivity
import com.samgithiaka.apps.foodforthehungry.ui.base.ChildModelFactory
import com.samgithiaka.apps.foodforthehungry.ui.viewmodel.ChildDetailsViewModel


class ChildProfileActivity : BaseActivity() {

    private val TAG = "ChildProfileActivity"
    private lateinit var viewModel: ChildDetailsViewModel
    private lateinit var binding : ActivityChildProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = activityContext?.let { DataBindingUtil.setContentView(it,R.layout.activity_child_profile) }!!
        initData()
    }


    private fun initData() {
        try{
            val dao = activityContext?.let { ChildDatabase.getInstance(it.application)?.childStorageDao() }
            val repository = dao?.let { ChildRepository(it) }
            val factory = repository?.let { ChildModelFactory(it) }
            viewModel = factory?.let {
                activityContext?.let { it1 ->
                    ViewModelProvider(
                        it1,
                        it
                    )
                }?.get(ChildDetailsViewModel::class.java)
            }!!

            val i = intent

            val child: SaveChildRoomModel? = i.getSerializableExtra("childObject") as SaveChildRoomModel?
            viewModel.inputFullName.value = child?.fullName
            viewModel.inputBirthCertNo.value = child?.birthCertificateNUmber
            viewModel.inputDateOfBirth.value = child?.dob
            viewModel.inputLocation.value = child?.location

            binding.viewModel = viewModel

            if (child != null) {

                viewModel.initUpdateAndDelete(child)
            }

            activityContext?.let {
                viewModel.getAction().observe(
                    it,
                    { action ->
                        if (action != null) {
                            handleAction(action)
                        }
                    })
            }
            setUpOnclickListener(child)
        }catch (e : Exception){
            Log.e(TAG, "initData: ",e )
        }
    }

    private fun setUpOnclickListener(child: SaveChildRoomModel?) {
        binding.btnEdit.setOnClickListener { proceedToEditChild(child) }
        binding.iwBack.setOnClickListener { finish() }
    }

    private fun proceedToEditChild(child: SaveChildRoomModel?) {
        try{
            val i = Intent(activityContext, EditChildActivity::class.java)
            i.putExtra("childObject", child)
            startActivity(i)
            finish()
        }catch (e: Exception){
            Log.e(TAG, "proceedToEditChild: ",e )
        }
    }

    private fun handleAction(action: Action) {
        when (action.getValue()) {
            Action.DELETE_CHILD -> {
                finish()
            }
        }
    }
}