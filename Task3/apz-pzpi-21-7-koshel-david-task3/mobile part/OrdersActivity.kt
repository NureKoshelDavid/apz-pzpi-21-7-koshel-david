package com.example.beautymanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautymanager.adapters.OrdersAdapter
import com.example.beautymanager.databinding.ActivityOrdersBinding
import com.example.beautymanager.models.Order
import com.example.beautymanager.models.SensorData
import com.example.beautymanager.models.User
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.sql.Date
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate

class OrdersActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var orders: MutableList<Order>
    private var addOrderLauncher : ActivityResultLauncher<Intent>? = null

    lateinit var binding: ActivityOrdersBinding
    lateinit var email: String
    lateinit var user: User
    var sensorData = SensorData("","","")

    private val handler = Handler(Looper.getMainLooper())
    private val fetchSensorsRunnable = object : Runnable {
        override fun run() {
            fetchSensors()
            handler.postDelayed(this, 10000) // Повторить через 10 секунд
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handler.post(fetchSensorsRunnable)
        //email = loadUser().email
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView)
        orders = mutableListOf()
        orders.add(Order("Order 1", "2024-11-11T11:11:11.000+00:00",100,1L,1L,1L,"В процесі"))
        orders.add(Order("Order 2", "2024-11-11T11:11:11.000+00:00",100,1L,1L,1L,"В процесі"))
        orders.add(Order("Order 3", "2024-11-11T11:11:11.000+00:00",100,1L,1L,1L,"В процесі"))
        ordersAdapter = OrdersAdapter(orders,this)

        ordersRecyclerView.layoutManager = LinearLayoutManager(this)
        ordersRecyclerView.adapter = ordersAdapter



        addOrderLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                orders.add(it.data?.getSerializableExtra("order") as Order)
                ordersAdapter.notifyDataSetChanged()
            }
        }

        //fetchUser()

        binding.btnGoAddOrder.setOnClickListener {
            val i = Intent(this@OrdersActivity, AddOrderActivity::class.java)
            i.putExtra("saloonId", user.saloonId)
            i.putExtra("staffId", user.id)

            Log.d("ORDER", "ORDERS saloonId from intent: ${i.getLongExtra("saloonId",0L)}")
            addOrderLauncher!!.launch(i)
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

        //fetchSensors()
    }

    private fun fetchUser() {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://10.0.2.2:8082/users/email/$email")
                .get()
                .build()

            client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ORDERS", "ERROR: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@OrdersActivity, "Ошибка загрузки пользователя", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val gson = Gson()
                    user = gson.fromJson(responseBody, User::class.java)
                    Log.d("ORDERS", user.saloonId.toString())

                    runOnUiThread {
                        fetchOrders()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@OrdersActivity, "Ошибка получения данных пользователя", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(fetchSensorsRunnable) // Остановить Runnable при уничтожении Activity
    }

    private fun fetchOrders() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8083/orders/all?saloonId=${user.saloonId}")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@OrdersActivity, "Ошибка загрузки заказов", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("ORDERS","ORDERS: $responseBody")
                if (response.isSuccessful && responseBody != null) {
                    val ordersList = Gson().fromJson(responseBody, Array<Order>::class.java).toList()
                    runOnUiThread {
                        orders.clear()
                        orders.addAll(ordersList)
                        ordersAdapter.notifyDataSetChanged()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@OrdersActivity, "Ошибка получения данных", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    fun loadUser() : User {
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE)
        val json = sharedPreferences.getString("USER_INFO",null)
        return Gson().fromJson(json,User::class.java)
    }

    fun fetchSensors(){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8099/data")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("ORDERS","ERROR SENSORS: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@OrdersActivity, "Помилка отримання данних SmartDevice", Toast.LENGTH_LONG).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("ORDERS","SENSOR DATA: $responseBody")
                if (response.isSuccessful && responseBody != null) {
                    sensorData = Gson().fromJson(responseBody, SensorData::class.java)
                    runOnUiThread {
                        binding.tvTemp.text = "Температура: \n${sensorData.temperature}*с"
                        binding.tvHumidity.text = "Вологість: \n${sensorData.humidity}%"
                        binding.tvLdr.text = "Освітлення: \n${sensorData.ldrValue} люмен"
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@OrdersActivity, "Помилка отримання данних SmartDevice", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
