package com.map.nguyen_ha_my.bt2.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

@Entity(tableName = "tblTransaction",
    foreignKeys = [ForeignKey(
        entity = InOut::class,
        parentColumns = ["id"],
        childColumns = ["idCatInOut"],
        onDelete = ForeignKey.CASCADE
    )])
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val idCatInOut: Int,
    val amount: Int,
    val date: Date,
    val note:String
)
