package com.samgithiaka.apps.foodforthehungry.ui.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.samgithiaka.apps.foodforthehungry.data.model.Action
import com.samgithiaka.apps.foodforthehungry.data.repository.ChildRepository
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveChildRoomModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ChildDetailsViewModel(private val repository: ChildRepository) : ViewModel(){

    val inputFullName = MutableLiveData<String>()
    val inputLocation = MutableLiveData<String>()
    val inputDateOfBirth = MutableLiveData<String>()
    val inputBirthCertNo = MutableLiveData<String>()
    private lateinit var childToUpdateOrDelete: SaveChildRoomModel
    private var isUpdateOrDelete = false

    fun saveOrUpdate() {
        if (isUpdateOrDelete) {
            childToUpdateOrDelete.fullName = inputFullName.value!!
            childToUpdateOrDelete.location = inputLocation.value!!
            childToUpdateOrDelete.dob = inputDateOfBirth.value!!
            childToUpdateOrDelete.birthCertificateNUmber = inputBirthCertNo.value!!
            updateChild(childToUpdateOrDelete)
        } else {
            val name = inputFullName.value!!
            val location = inputLocation.value!!
            val dob = inputDateOfBirth.value!!
            val birthCertNo = inputBirthCertNo.value!!
            insertChild(SaveChildRoomModel(0, name, dob, location, birthCertNo))
            inputFullName.value = ""
            inputLocation.value = ""
            inputDateOfBirth.value = ""
            inputBirthCertNo.value = ""
        }
    }

    fun updateChild(){
        childToUpdateOrDelete.fullName = inputFullName.value!!
        childToUpdateOrDelete.location = inputLocation.value!!
        childToUpdateOrDelete.dob = inputDateOfBirth.value!!
        childToUpdateOrDelete.birthCertificateNUmber = inputBirthCertNo.value!!
        updateChild(childToUpdateOrDelete)
    }

    private fun updateChild(child: SaveChildRoomModel) = viewModelScope.launch {
        val noOfRows = repository.update(child)
        if (noOfRows > 0) {
            mAction.value = Action(Action.SHOW_CHILD);
            inputFullName.value = ""
            inputLocation.value = ""
            inputDateOfBirth.value = ""
            inputBirthCertNo.value = ""
            isUpdateOrDelete = false
            Log.e("TAG", "updateChild: $noOfRows Row Updated Successfully", )
        } else {
            Log.e("TAG", "updateChild: Error Occurred" )
        }
    }

    fun initUpdateAndDelete(child: SaveChildRoomModel) {
        inputFullName.value = child.fullName!!
        inputLocation.value = child.location!!
        inputDateOfBirth.value = child.dob!!
        inputBirthCertNo.value = child.birthCertificateNUmber!!
        childToUpdateOrDelete = child
    }

    fun deleteChild(){
        deleteChild(childToUpdateOrDelete)
    }

    private fun insertChild(child: SaveChildRoomModel) = viewModelScope.launch {
        repository.insert(child)
        mAction.setValue(Action(Action.SHOW_CHILD));
    }

    private fun deleteChild(child: SaveChildRoomModel) = viewModelScope.launch {
        Log.e("TAG", "deleteChild:model: $child" )
        val noOfRowsDeleted = repository.delete(child)
        if (noOfRowsDeleted > 0) {
            Log.e("TAG", "deleteChild: $noOfRowsDeleted Children Deleted Successfully" )
        } else {
            Log.e("TAG", "deleteChild: Error Occured" )
        }
            mAction.setValue(Action(Action.DELETE_CHILD));
    }

    fun getSavedChildren() = liveData {
        repository.children.collect{emit(it)}
    }

    fun getSearchedChildren(child : String) = liveData {
        repository.searchChild(child).collect{emit(it)}
    }

    private val mAction: MutableLiveData<Action> = MutableLiveData<Action>()

    fun getAction(): LiveData<Action> {
        return mAction
    }

}