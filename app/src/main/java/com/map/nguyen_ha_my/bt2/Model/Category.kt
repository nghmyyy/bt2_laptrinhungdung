package com.map.nguyen_ha_my.bt2.Model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tblCategory",
foreignKeys = [
    ForeignKey(
    entity = Category::class,
    parentColumns = ["id"],
    childColumns = ["idparent"],
    onDelete = ForeignKey.CASCADE
),
])
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idparent: Int = 0,// Khóa chính
    val name: String, // Tên danh mục
    val note: String, // Ghi chú
    val inOut: Boolean  // in or out=))))
)
