package com.samgithiaka.apps.foodforthehungry.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.samgithiaka.apps.foodforthehungry.data.model.Action
import com.samgithiaka.apps.foodforthehungry.data.repository.SponsorsRepository
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveSponsorRoomModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class SponsorDetailsViewModel(private val repository: SponsorsRepository) : ViewModel(){
    val inputFullName = MutableLiveData<String>()
    val inputLocation = MutableLiveData<String>()
    val inputPhoneNumber= MutableLiveData<String>()
    val inputPassportNumber = MutableLiveData<String>()
    private lateinit var sponsorToUpdateOrDelete: SaveSponsorRoomModel
    private var isUpdateOrDelete = false

    fun saveOrUpdate() {
        val name = inputFullName.value!!
        val location = inputLocation.value!!
        val phoneNumber = inputPhoneNumber.value!!
        val passportNumber = inputPassportNumber.value!!
        insertChildSponsor(SaveSponsorRoomModel(0, name, phoneNumber,location,passportNumber))
        inputFullName.value = ""
        inputLocation.value = ""
        inputPhoneNumber.value = ""
        inputPassportNumber.value = ""
    }

    fun updateSponsor(){
        sponsorToUpdateOrDelete.fullName = inputFullName.value!!
        sponsorToUpdateOrDelete.location = inputLocation.value!!
        sponsorToUpdateOrDelete.passportNumber = inputPassportNumber.value!!
        sponsorToUpdateOrDelete.phoneNumber = inputPhoneNumber.value!!
        updateSponsor(sponsorToUpdateOrDelete)
    }

    private fun updateSponsor(sponsor: SaveSponsorRoomModel) = viewModelScope.launch {
        val noOfRows = repository.update(sponsor)
        if (noOfRows > 0) {
            mAction.value = Action(Action.SHOW_SPONSOR);
            inputFullName.value = ""
            inputLocation.value = ""
            inputPhoneNumber.value = ""
            inputPassportNumber.value = ""
            isUpdateOrDelete = false
            Log.e("TAG", "updateSponsor: $noOfRows Row Updated Successfully", )
        } else {
            Log.e("TAG", "updateSponsor: Error Occurred" )
        }
    }

    fun initUpdateAndDelete(sponsor: SaveSponsorRoomModel) {
        inputFullName.value = sponsor.fullName!!
        inputLocation.value = sponsor.location!!
        inputPassportNumber.value = sponsor.passportNumber!!
        inputPhoneNumber.value = sponsor.phoneNumber!!
        sponsorToUpdateOrDelete = sponsor
    }

    fun deleteSponsor(){
        deleteSponsor(sponsorToUpdateOrDelete)
    }

    private fun deleteSponsor(sponsor: SaveSponsorRoomModel) = viewModelScope.launch {
        Log.e("TAG", "deleteSponsor:model: $sponsor" )
        val noOfRowsDeleted = repository.delete(sponsor)
        if (noOfRowsDeleted > 0) {
            Log.e("TAG", "deleteSponsor: $noOfRowsDeleted Sponsors Deleted Successfully" )
        } else {
            Log.e("TAG", "deleteSponsor: Error Occured" )
        }
        mAction.setValue(Action(Action.DELETE_SPONSOR));
    }

    private fun insertChildSponsor(sponsor: SaveSponsorRoomModel) = viewModelScope.launch {
        repository.insert(sponsor)
        mAction.setValue(Action(Action.SHOW_SPONSOR));
    }

    fun getSavedSponsors() = liveData {
        repository.sponsors.collect{emit(it)}
    }

    fun getSearchedSponsor(sponsor : String) = liveData {
        repository.searchSponsor(sponsor).collect{emit(it)}
    }

    private val mAction: MutableLiveData<Action> = MutableLiveData<Action>()

    fun getAction(): LiveData<Action> {
        return mAction
    }

}