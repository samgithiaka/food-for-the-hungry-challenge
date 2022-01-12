package com.samgithiaka.apps.foodforthehungry.data.room.room_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SaveChildRoomModel constructor (
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "fullName") var fullName: String? = null,
    @ColumnInfo(name = "dob") var dob: String? = null,
    @ColumnInfo(name = "location") var location: String? = null,
    @ColumnInfo(name = "birthCertificateNUmber") var birthCertificateNUmber: String? = null
) : Serializable




