package com.map.nguyen_ha_my.bt2.Database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.map.nguyen_ha_my.bt2.DAO.CategoryDAO
import com.map.nguyen_ha_my.bt2.DAO.TransactionDAO
import com.map.nguyen_ha_my.bt2.Model.CatInOut
import com.map.nguyen_ha_my.bt2.Model.Category
import com.map.nguyen_ha_my.bt2.Model.Converters
import com.map.nguyen_ha_my.bt2.Model.InOut
import com.map.nguyen_ha_my.bt2.Model.Transaction

@Database(entities = [Category::class, CatInOut::class, InOut::class, Transaction::class], version = 1)
@TypeConverters(Converters::class) // Thêm @TypeConverters để sử dụng lớp Converters
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDAO(): CategoryDAO
    abstract fun transactionDAO(): TransactionDAO

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch {
                val categoryDao = INSTANCE?.categoryDAO()
                categoryDao?.insertCategories(getDefaultCategories())
            }
        }

        private fun getDefaultCategories(): List<Category> {
            return listOf(
                Category(name = "Lương", note = "Salary", inOut = true),
                Category(name = "Chi tiêu hàng ngày", note = "Daily Spending", inOut = false),
                Category(name = "Học phí", note = "Tuition", inOut = false),
                Category(name = "Làm thêm", note = "Part-time", inOut = true),
                Category(name = "Học bổng", note = "Scholarship", inOut = true),
                Category(name = "Bố mẹ cho", note = "From Parent", inOut = true),
                Category(name = "Quà tặng", note = "Gift", inOut = true),
                // Danh mục con

                Category(name = "Tiền nhà", note = "Rent", inOut = false, idparent = 2),
                Category(name = "Tiền điện", note = "Electricity", inOut = false, idparent = 2),
                Category(name = "Tiền nước", note = "Water", inOut = false, idparent = 2),
                Category(name = "Tiền điện thoại", note = "Daily Spending", inOut = false, idparent =2),
                Category(name = "Tiền ăn", note = "Daily Spending", inOut = false, idparent = 2),
                Category(name = "Tiền đi chợ", note = "Daily Spending", inOut = false, idparent = 2),
            )
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(AppDatabaseCallback(scope)) // Thêm callback
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}
