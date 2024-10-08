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
                Category(name = "Làm thêm", note = "Part-time", inOut = true),
                Category(name = "Học bổng", note = "Scholarship", inOut = true),
                Category(name = "Bố mẹ cho", note = "From Parent", inOut = true),
                Category(name = "Quà tặng", note = "Gift", inOut = true),
                Category(name = "Học phí", note = "tuition", inOut = false),
                Category(name = "Tiền nhà", note = "Daily Spending", inOut = false),
                Category(name = "Tiền điện", note = "Daily Spending", inOut = false),
                Category(name = "Tiền nước", note = "Daily Spending", inOut = false),
                Category(name = "Tiền điện thoại", note = "Daily Spending", inOut = false),
                Category(name = "Tiền ăn", note = "Daily Spending", inOut = false),
                Category(name = "Tiền đi chợ", note = "Daily Spending", inOut = false),
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
