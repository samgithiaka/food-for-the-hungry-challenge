package com.samgithiaka.apps.foodforthehungry.data.room.dbs

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samgithiaka.apps.foodforthehungry.data.room.interfaces.SaveSponsorDetailsStorageDao
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveSponsorRoomModel

@Database(
    entities = [SaveSponsorRoomModel::class],
    exportSchema = false,
    version = 1
)
abstract class SponsorsDatabase : RoomDatabase() {
    abstract fun sponsorStorageDao():
            SaveSponsorDetailsStorageDao?

    companion object {
        private val TAG = SponsorsDatabase::class.java.simpleName
        private const val DB_SAVE_SPONSOR = "db_save_sponsor"
        private var instance: SponsorsDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): SponsorsDatabase? {
            return try {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SponsorsDatabase::class.java, DB_SAVE_SPONSOR
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
                instance
            } catch (e: Exception) {
                Log.e(TAG, "getInstance: " + e.message)
                null
            }
        }
    }
}