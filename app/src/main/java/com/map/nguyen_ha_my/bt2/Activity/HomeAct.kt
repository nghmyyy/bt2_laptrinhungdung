package com.map.nguyen_ha_my.bt2.Activity

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.map.nguyen_ha_my.bt2.Adapter.TransactionAdapter
import com.map.nguyen_ha_my.bt2.DAO.CategoryDAO
import com.map.nguyen_ha_my.bt2.DAO.TransactionDAO
import com.map.nguyen_ha_my.bt2.Database.AppDatabase
import com.map.nguyen_ha_my.bt2.Model.Transaction
import com.map.nguyen_ha_my.bt2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.sql.Date
import java.util.Locale

class HomeAct : AppCompatActivity() {
    private lateinit var textDate: TextView;
    private lateinit var txtSum: TextView;
    private lateinit var listTransaction: ListView;
    private lateinit var btnAdd: Button;
    private lateinit var transactionDAO: TransactionDAO
    private lateinit var categoryDAO: CategoryDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        textDate = findViewById(R.id.textDate);
        txtSum = findViewById(R.id.txtSum);
        listTransaction = findViewById(R.id.listTransaction);
        btnAdd = findViewById(R.id.btnAdd);

        //set Date la ngay hom nay
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        textDate.text = dateFormat.format(currentDate)

        //khoitaoDao
        transactionDAO = AppDatabase.getDatabase(applicationContext).transactionDAO()
        categoryDAO = AppDatabase.getDatabase(applicationContext).categoryDAO()

        //doi sang Date cua sql
        val sqlDate = Date(currentDate.time)
        //tinh tong giao dich trong ngay
        caculTotalForToday(sqlDate)

        //hien thi listview
        loadTransactionToday(sqlDate)

        //xulisukienbutton
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddAct::class.java)
            startActivity(intent)
        }





    }
    private fun caculTotalForToday(date: Date){
        CoroutineScope(Dispatchers.IO).launch {
            val total = transactionDAO.getTotalByDate(date)
            withContext(Dispatchers.Main){
                txtSum.text = total?.toString() ?: "0"
            }
        }
    }

    private fun loadTransactionToday(date: Date){
        CoroutineScope(Dispatchers.IO).launch {
            val transactions = transactionDAO.search(date)
            withContext(Dispatchers.Main){
                if(transactions.isNotEmpty()){
                    //tao adapter cho list
                    val adapter = TransactionAdapter(
                        this@HomeAct, transactions, R.layout.list_items
                    )
                    listTransaction.adapter = adapter
                }
            }
        }
    }
}