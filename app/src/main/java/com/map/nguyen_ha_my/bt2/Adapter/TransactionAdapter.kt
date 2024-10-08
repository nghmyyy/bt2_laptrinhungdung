package com.map.nguyen_ha_my.bt2.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.map.nguyen_ha_my.bt2.Model.Transaction
import com.map.nguyen_ha_my.bt2.R
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter(
    private val context: Context,
    private val listTransaction: List<Transaction>,
    private val layoutId: Int,
) : BaseAdapter() {

    override fun getCount(): Int {
        return listTransaction.size
    }

    override fun getItem(p0: Int): Any {
        // Trả về đối tượng Transaction ở vị trí p0
        return listTransaction[p0]
    }

    override fun getItemId(p0: Int): Long {
        // Trả về id của Transaction ở vị trí p0
        return listTransaction[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        // Kiểm tra nếu convertView (p1) là null, nếu đúng thì nạp layout mới
        val view = p1 ?: LayoutInflater.from(context).inflate(layoutId, p2, false)

        // Lấy transaction hiện tại
        val todayTransaction = listTransaction[p0]

        // Ánh xạ view con từ View
        val txtCat = view.findViewById<TextView>(R.id.txtCat)
        val txtAmount = view.findViewById<TextView>(R.id.txtAmount)
        val txtNote_listitems = view.findViewById<TextView>(R.id.txtNote_listitems)
        val txtDate_listitems = view.findViewById<TextView>(R.id.txtDate_listitems)

        // Gán dữ liệu vào các view
        txtCat.text = todayTransaction.name
        txtAmount.text = todayTransaction.amount.toString()
        txtNote_listitems.text = todayTransaction.note

        // Định dạng ngày với SimpleDateFormat
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        txtDate_listitems.text = dateFormat.format(todayTransaction.date)

        return view
    }
}
