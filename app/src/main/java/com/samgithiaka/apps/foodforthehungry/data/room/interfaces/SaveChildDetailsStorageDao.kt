package com.samgithiaka.apps.foodforthehungry.data.room.interfaces

import androidx.room.*
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveChildRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveChildDetailsStorageDao {
    @Query("SELECT * FROM savechildroommodel")
    fun loadAllChildren(): Flow<List<SaveChildRoomModel>>

    @Query("SELECT * FROM savechildroommodel WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<SaveChildRoomModel?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChildRecord(savechildroommodel: SaveChildRoomModel)

    @Query("SELECT * FROM savechildroommodel  WHERE fullName LIKE :query")
    fun getChildName(query: String): Flow<List<SaveChildRoomModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg savechildroommodels: SaveChildRoomModel?)

    @Update
    fun updateChildDetails(savechildroommodel: SaveChildRoomModel?): Int

    @Delete
    fun delete(savechildroommodel: SaveChildRoomModel?): Int

    @Query("DELETE FROM savechildroommodel")
    fun deleteAll(): Int

    @Query("SELECT COUNT(birthCertificateNUmber) FROM savechildroommodel")
    fun getRowCount(): Int?
}