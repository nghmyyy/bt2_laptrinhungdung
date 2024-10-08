package com.map.nguyen_ha_my.bt2.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tblInOut")
data class InOut (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)