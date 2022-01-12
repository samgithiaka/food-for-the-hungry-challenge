package com.samgithiaka.apps.foodforthehungry.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samgithiaka.apps.foodforthehungry.data.repository.ChildRepository
import com.samgithiaka.apps.foodforthehungry.ui.viewmodel.ChildDetailsViewModel

class ChildModelFactory(private val repository: ChildRepository): ViewModelProvider.Factory{
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChildDetailsViewModel::class.java)){
            return ChildDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}