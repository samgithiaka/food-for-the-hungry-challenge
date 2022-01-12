package com.samgithiaka.apps.foodforthehungry.data.repository

import com.samgithiaka.apps.foodforthehungry.data.room.interfaces.SaveChildDetailsStorageDao
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveChildRoomModel
import kotlinx.coroutines.flow.Flow

class ChildRepository (private val childDao: SaveChildDetailsStorageDao) {

    val children = childDao.loadAllChildren()

    fun insert(child: SaveChildRoomModel) {
        childDao.insertChildRecord(child)
    }

    fun delete(child: SaveChildRoomModel): Int {
        return childDao.delete(child)
    }

    fun update(child: SaveChildRoomModel): Int{
        return childDao.updateChildDetails(child)
    }

    fun searchChild(child: String) : Flow<List<SaveChildRoomModel>> {
        return childDao.getChildName(child)
    }
}