package com.map.nguyen_ha_my.bt2.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.map.nguyen_ha_my.bt2.Model.Transaction
import java.util.Date

@Dao
interface TransactionDAO {
    //them transection
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: Transaction)

    //edit transection
    @Update
    fun update(transaction: Transaction)

    //delete
    @Delete
    fun delete(transaction: Transaction)

    //tim bang date
    @Query("Select * from tblTransaction where date = :d")
    fun search(d: java.sql.Date): List<Transaction>

    //lay tong tien theo ngay
    @Query("Select SUM(amount) From tblTransaction where date = :d")
    fun getTotalByDate(d: java.sql.Date): Long?

}