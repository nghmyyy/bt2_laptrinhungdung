package com.map.nguyen_ha_my.bt2.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tblCatInOut",
    foreignKeys = [ForeignKey (
            entity = InOut::class,
        parentColumns = ["id"],
        childColumns = ["inOutId"],
        onDelete = ForeignKey.CASCADE),
    ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["idCat"]
    )])
data class CatInOut (
    @PrimaryKey(autoGenerate = true) val id: Int= 0,
    val idCat: Int,
    val inOutId: Int
    )