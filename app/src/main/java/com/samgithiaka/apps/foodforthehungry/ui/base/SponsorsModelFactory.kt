package com.samgithiaka.apps.foodforthehungry.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samgithiaka.apps.foodforthehungry.data.repository.SponsorsRepository
import com.samgithiaka.apps.foodforthehungry.ui.viewmodel.SponsorDetailsViewModel

class SponsorsModelFactory(private val repository: SponsorsRepository): ViewModelProvider.Factory{
      override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SponsorDetailsViewModel::class.java)){
            return SponsorDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}