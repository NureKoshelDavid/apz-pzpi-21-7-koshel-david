package com.example.beautymanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beautymanager.databinding.ActivityAddOrderBinding
import com.example.beautymanager.databinding.ActivityOrderInfoBinding
import com.example.beautymanager.models.Order
import com.example.beautymanager.models.Saloon
import com.example.beautymanager.models.User
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AddOrderActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: ActivityAddOrderBinding
    lateinit var saloon : Saloon //Saloon(1L,"","","","","","",1L,true)
    var name : String = ""
    var totalCost : String = ""
    var date : String = ""
    var time : String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //HTTP запрос для получения салона и создания заказа
        fetchSaloon()

        binding.btnAddOrder.setOnClickListener{
            name = binding.etName.text.toString()
            totalCost = binding.etTotalCost.text.toString()
            date = binding.etDate.text.toString()
            time = binding.etTime.text.toString()

            Log.d("ORDER","SALOON id : ${saloon.id}")
            Log.d("ORDER","Totat cost : ${totalCost}")
            var order = Order(name, getDateTime(date,time),totalCost.toInt(),saloon.ownerId,saloon.id,intent.getLongExtra("staffId",0L),"В процесі")
            addOrder(order)
            var addIntent = Intent()
            Log.d("ORDER","ORDER: ${order}")
            addIntent.putExtra("order",order)
            setResult(RESULT_OK,addIntent)
            finish()
        }

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

    fun fetchSaloon(){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8081/saloons/${loadUser().saloonId}")
            .get()
            .build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ORDER",e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful)
                {
                    saloon = Gson().fromJson(response.body?.string(), Saloon::class.java)
                } else{
                    Log.d("ORDER", response.body?.string().toString())
                    runOnUiThread {
                        Toast.makeText(this@AddOrderActivity, "Помилка при отриманні салону", Toast.LENGTH_LONG).show()
                    }

                }
            }
        })
    }

    fun addOrder(order : Order){
        Log.d("ORDER","Date Time: ${date} ${time} SUM: ${date+time}")
        val client = OkHttpClient()
        val json = Gson().toJson(order)
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        val orderRequest = Request.Builder()
            .url("http://10.0.2.2:8083/orders")
            .post(requestBody)
            .build()

        client.newCall(orderRequest).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ORDER", "ADD ERROR ${e.message.toString()}")
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful)
                {
                    runOnUiThread{
                        Toast.makeText(this@AddOrderActivity, "Замовлення успішно додано", Toast.LENGTH_LONG).show()
                    }
                } else
                    runOnUiThread {
                        Toast.makeText(this@AddOrderActivity, "Помилка при створенні замовлення", Toast.LENGTH_LONG).show()
                    }
            }
        })
    }

    fun loadUser() : User {
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE)
        val json = sharedPreferences.getString("USER_INFO",null)
        return Gson().fromJson(json, User::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateTime(date: String, time : String) : String {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        // Преобразуем строку даты в объект LocalDate
        val localDate = LocalDate.parse(date, dateFormatter)

        // Определяем формат входной строки времени
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        // Преобразуем строку времени в объект LocalTime
        val localTime = LocalTime.parse(time, timeFormatter)

        // Комбинируем дату и время в нужном формате
        return "${localDate}T${localTime}:00.000+00:00"
    }
}