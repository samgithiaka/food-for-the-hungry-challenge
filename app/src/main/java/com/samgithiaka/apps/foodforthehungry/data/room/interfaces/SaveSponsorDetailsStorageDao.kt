package com.samgithiaka.apps.foodforthehungry.data.room.interfaces

import androidx.room.*
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveSponsorRoomModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveSponsorDetailsStorageDao {
    @Query("SELECT * FROM savesponsorroommodel")
    fun loadAllSPonsors(): Flow<List<SaveSponsorRoomModel>>

    @Query("SELECT * FROM savesponsorroommodel WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<SaveSponsorRoomModel?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSponsorRecord(savesponsorroommodel: SaveSponsorRoomModel)

    @Query("SELECT * FROM savesponsorroommodel  WHERE fullName LIKE :query")
    fun getSponsorName(query: String): Flow<List<SaveSponsorRoomModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg savesponsorroommodels: SaveSponsorRoomModel?)

    @Update
    fun updateSponsorDetails(savesponsorroommodel: SaveSponsorRoomModel?) : Int

    @Delete
    fun delete(savesponsorroommodel: SaveSponsorRoomModel?) : Int

    @Query("DELETE FROM savesponsorroommodel")
    fun deleteAll()

    @Query("SELECT COUNT(passportNumber) FROM savesponsorroommodel")
    fun getRowCount(): Int?
}