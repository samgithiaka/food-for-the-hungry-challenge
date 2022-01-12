package com.samgithiaka.apps.foodforthehungry.data.room.room_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SaveSponsorRoomModel constructor(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "fullName") var fullName: String? = null,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String? = null,
    @ColumnInfo(name = "location") var location: String? = null,
    @ColumnInfo(name = "passportNumber") var passportNumber: String? = null
) : Serializable


