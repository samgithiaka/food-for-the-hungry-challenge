package com.samgithiaka.apps.foodforthehungry.data.room.dbs

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.samgithiaka.apps.foodforthehungry.data.room.interfaces.SaveChildDetailsStorageDao
import com.samgithiaka.apps.foodforthehungry.data.room.room_model.SaveChildRoomModel

@Database(
    entities = [SaveChildRoomModel::class],
    exportSchema = false,
    version = 3
)
abstract class ChildDatabase : RoomDatabase() {
    abstract fun childStorageDao():
            SaveChildDetailsStorageDao?

    companion object {
        private val TAG = ChildDatabase::class.java.simpleName
        private const val DB_SAVE_CHILD = "db_save_child"
        private var instance: ChildDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): ChildDatabase? {
            return try {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChildDatabase::class.java, DB_SAVE_CHILD
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