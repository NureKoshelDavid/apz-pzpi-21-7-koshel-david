package com.example.beautymanager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.beautymanager.databinding.ActivityOrderInfoBinding
import com.example.beautymanager.models.Order
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class OrderInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderInfoBinding
    lateinit var order: Order
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        order = Order(intent.getStringExtra("name").toString(),intent.getStringExtra("datetime").toString(),intent.getStringExtra("totalCost")!!.toInt(),intent.getLongExtra("ownerId",0L),intent.getLongExtra("saloonId",0L),intent.getLongExtra("staffId",0L),intent.getStringExtra("status").toString(),intent.getStringExtra("id")!!.toLong())

        val formattedDateTime = convertDateString(intent.getStringExtra("datetime").toString())
        binding.tvId.text = "Замовлення №${intent.getStringExtra("id")}"
        binding.tvName.text = "Послуга: ${intent.getStringExtra("name")}"
        binding.tvDatetime.text = formattedDateTime
        binding.tvTotalcost.text = "Встановлена вартість: ${intent.getStringExtra("totalCost")} грн"
        binding.tvStaff.text = "Працівник: ${intent.getStringExtra("staffLastname")} ${intent.getStringExtra("staffName")} ${intent.getStringExtra("staffPatronymic")}"
        binding.tvStatus.text = "Cтатус замовлення: ${intent.getStringExtra("status")}"

        binding.btnCancelOrder.setOnClickListener{
            cancelOrder(intent.getStringExtra("id").toString().toLong())
            val intent = Intent(this,OrdersActivity::class.java)
            startActivity(intent)
            finish()

        }
        binding.btnChangeStatus.setOnClickListener{
            changeOrderStatus(intent.getStringExtra("id").toString().toLong(),order)
            if (intent.getStringExtra("status").equals("В процесі"))
                binding.tvStatus.text = "Cтатус замовлення: Виконано"
            else
                binding.tvStatus.text = "Cтатус замовлення: В процесі"
        }

        setNavbarListener()
    }

    fun cancelOrder(id: Long) {
        val client = OkHttpClient()
        val orderRequest = Request.Builder()
            .url("http://10.0.2.2:8083/orders/$id")
            .delete()
            .build()

        client.newCall(orderRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ORDER INFO", "DELETE ERROR ${e.message.toString()}")
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful && response.body?.string().toBoolean()) {
                    runOnUiThread {
                        Toast.makeText(this@OrderInfoActivity, "Замовлення скасовано", Toast.LENGTH_LONG).show()
                    }
                } else
                    runOnUiThread {
                        Toast.makeText(this@OrderInfoActivity, "Помилка при видаленні замовлення", Toast.LENGTH_LONG).show()
                    }
            }
        })

    }

    fun changeOrderStatus(id: Long,order: Order){
        if(order.status=="В процесі")
            order.status="Виконано"
        else
            order.status="В процесі"

        val client = OkHttpClient()
        val json = Gson().toJson(order)
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        val orderRequest = Request.Builder()
            .url("http://10.0.2.2:8083/orders/$id")
            .put(requestBody)
            .build()

        Log.d("ORDER INFO", "ORDER ${json}")
        client.newCall(orderRequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ORDER INFO", "CHANGE ERROR ${e.message.toString()}")
            }
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody.toBoolean()) {
                    Log.d("ORDER INFO", "CHANGE: ${responseBody}")
                    runOnUiThread {
                        Toast.makeText(this@OrderInfoActivity, "Cтатус змінено", Toast.LENGTH_LONG).show()
                    }
                } else
                    Log.d("ORDER INFO", "CHANGE: ${responseBody}")
                    runOnUiThread {
                        //Toast.makeText(this@OrderInfoActivity, "Помилка при зміні статусу", Toast.LENGTH_LONG).show()
                    }
            }
        })
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

    private fun setNavbarListener() {
        binding.bNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu -> {

                    val intent = Intent(this, OrdersActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.add_order -> {
                    // Переход на страницу добавления заказа
                    val intent = Intent(this, AddOrderActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.profile -> {
                    // Переход на страницу профиля
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }
    }
}