package com.samgithiaka.apps.foodforthehungry.data.repository

import com.samgithiaka.apps.foodforthehungry.data.room.interfaces.SaveSponsorDetailsStorageDao
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveSponsorRoomModel
import kotlinx.coroutines.flow.Flow

class SponsorsRepository (private val sponsorsDao: SaveSponsorDetailsStorageDao) {

    val sponsors = sponsorsDao.loadAllSPonsors()

    fun insert(sponsor: SaveSponsorRoomModel) {
        sponsorsDao.insertSponsorRecord(sponsor)
    }

    fun delete(sponsor: SaveSponsorRoomModel) : Int{
        return sponsorsDao.delete(sponsor)
    }

    fun update(sponsor: SaveSponsorRoomModel): Int{
       return sponsorsDao.updateSponsorDetails(sponsor)
    }

    fun searchSponsor(sponsor: String) : Flow<List<SaveSponsorRoomModel>> {
        return sponsorsDao.getSponsorName(sponsor)
    }
}