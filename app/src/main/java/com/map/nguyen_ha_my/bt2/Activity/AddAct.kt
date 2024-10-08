package com.map.nguyen_ha_my.bt2.Activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.map.nguyen_ha_my.bt2.Database.AppDatabase
import com.map.nguyen_ha_my.bt2.Model.Category
import com.map.nguyen_ha_my.bt2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAct : AppCompatActivity() {
    private lateinit var radioGroupInOut: RadioGroup
    private lateinit var spinnerCat: Spinner
    private lateinit var editTextNumberSigned: EditText
    private lateinit var editTextDate: EditText
    private lateinit var editTextNote: EditText
    private lateinit var appDatabase: AppDatabase
    private lateinit var categories: List<Category>
    private var isRevenue: Boolean = true // Mặc định là true (Revenue)
    private lateinit var filteredCategories: List<Category> // Danh sách danh mục đã lọc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add)

        // Khởi tạo database
        appDatabase = AppDatabase.getDatabase(this, CoroutineScope(Dispatchers.IO))

        // Khởi tạo Spinner và RadioGroup
        spinnerCat = findViewById(R.id.spinnerCat)
        radioGroupInOut = findViewById(R.id.radioGroupInOut)

        // Lấy danh mục từ Database
        lifecycleScope.launch {
            categories = appDatabase.categoryDAO().getAllCategories()
            setupSpinner(categories) // Thiết lập Spinner với tất cả danh mục
        }

        // Thiết lập sự kiện cho RadioGroup
        radioGroupInOut.setOnCheckedChangeListener { group, checkedId ->
            isRevenue = when (checkedId) {
                R.id.radBtnRevenue -> true  // Nếu chọn Revenue
                R.id.radBtnExpenditure -> false  // Nếu chọn Expenditure
                else -> true
            }
            // Cập nhật danh sách danh mục khi chọn radio button
            filterCategories()
        }
    }

    private fun setupSpinner(categories: List<Category>) {
        this.categories = categories
        filterCategories() // Lọc danh mục theo trạng thái của isRevenue

        // Tạo adapter cho Spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filteredCategories.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCat.adapter = adapter

        // Thiết lập sự kiện khi chọn một mục
        spinnerCat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCategory = filteredCategories[position]
                // Làm gì đó với danh mục đã chọn, ví dụ:
                println("Selected Category: ${selectedCategory.name}, InOut: $isRevenue")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Xử lý nếu không có gì được chọn
            }
        }
    }

    private fun filterCategories() {
        filteredCategories = if (isRevenue) {
            // Lọc danh mục với inOut = true
            categories.filter { it.inOut }
        } else {
            // Lọc danh mục với inOut = false
            categories.filter { !it.inOut }
        }
        // Cập nhật adapter với danh sách đã lọc
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, filteredCategories.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCat.adapter = adapter
    }
}
