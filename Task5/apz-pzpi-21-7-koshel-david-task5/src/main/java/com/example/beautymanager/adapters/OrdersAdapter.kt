package com.example.beautymanager.adapters

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.beautymanager.OrderInfoActivity
import com.example.beautymanager.R
import com.example.beautymanager.models.Order
import com.example.beautymanager.models.User
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.userAgent
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class OrdersAdapter(private val orders: List<Order>, var context : Context) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {


    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val datetime: TextView = itemView.findViewById(R.id.tvDatetime)
        val totalCost: TextView = itemView.findViewById(R.id.tvTotalCost)
        val btnLayout: ConstraintLayout = itemView.findViewById(R.id.btnLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.name.text = order.name
        holder.datetime.text = convertDateString(order.datetime.toString())
        holder.totalCost.text = order.totalCost.toString() +" грн"

        holder.btnLayout.setOnClickListener{
            fetchUser(orders[position].staffId,position)
        }
    }

    private fun fetchUser(id: Long,position: Int){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8082/users/${id}")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ADAPTER", "ERROR: ${e.message}")
                    //Toast.makeText(this@OrdersActivity, "Ошибка загрузки пользователя", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val user = Gson().fromJson(responseBody, User::class.java)
                    Log.d("ADAPTER", "USER: ${responseBody}")
                    setIntent(position,user)
                } else {
                        //Toast.makeText(this@OrdersActivity, "Ошибка получения данных пользователя", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun setIntent(position: Int,user: User){
        val intent = Intent(context,OrderInfoActivity::class.java)

        intent.putExtra("staffName",user.name)
        intent.putExtra("staffLastname",user.surname)
        intent.putExtra("staffPatronymic",user.patronymic)
        intent.putExtra("id",orders[position].id.toString())
        intent.putExtra("name",orders[position].name)
        intent.putExtra("datetime",orders[position].datetime.toString())
        intent.putExtra("totalCost",orders[position].totalCost.toString())
        intent.putExtra("ownerId",orders[position].ownerId)
        intent.putExtra("saloonId",orders[position].saloonId)
        intent.putExtra("staffId",orders[position].staffId)
        intent.putExtra("status",orders[position].status)

        context.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateString(dateString: String): String {
        // Исходный формат даты
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        // Желаемый формат даты
        val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")

        // Парсинг даты из строки в ZonedDateTime
        val date = ZonedDateTime.parse(dateString, inputFormatter)
        // Форматирование даты в нужный формат
        return date.format(outputFormatter)
    }

    override fun getItemCount() = orders.size
}