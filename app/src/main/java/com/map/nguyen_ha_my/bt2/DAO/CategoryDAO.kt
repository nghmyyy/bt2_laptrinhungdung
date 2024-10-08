package com.map.nguyen_ha_my.bt2.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.map.nguyen_ha_my.bt2.Model.Category

@Dao
interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

    @Query("SELECT * FROM tblCategory")
    @Transactional
    suspend fun getAllCategories(): List<Category>
}

annotation class Transactional
