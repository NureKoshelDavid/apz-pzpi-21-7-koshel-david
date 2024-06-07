package com.example.beautymanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beautymanager.databinding.ActivityAddOrderBinding
import com.example.beautymanager.databinding.ActivityProfileBinding
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

class ProfileActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding: ActivityProfileBinding
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = loadUser()
        binding.etName.setText(user.name)
        binding.etLastname.setText(user.surname)
        binding.etPatronymic.setText(user.patronymic)
        binding.etEmail.setText(user.email)
        binding.etPhone.setText(user.phone)


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

    private fun loadUser() : User {
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE)
        val json = sharedPreferences.getString("USER_INFO",null)
        return Gson().fromJson(json,User::class.java)
    }

    fun onClickSaveUser(view : View){
        user.name = binding.etName.text.toString()
        user.surname = binding.etLastname.text.toString()
        user.patronymic = binding.etPatronymic.text.toString()
        user.email = binding.etEmail.text.toString()
        user.phone = binding.etPhone.text.toString()

        Log.d("USER SAVE", "ERROR: ${user.surname}")

        val json = Gson().toJson(user)
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_INFO",json)
        editor.apply()

        val client = OkHttpClient()
        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
        val request = Request.Builder()
            .url("http://10.0.2.2:8082/users/${user.id}")
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("USER SAVE", "ERROR: ${e.message}")
            }
            override fun onResponse(call: Call, response: Response) {
                Log.d("USER SAVE", "INFO: ${response.body?.string()}")
            }
        })
    }

    fun onClickLogOut(view:View){
        val intent = Intent(this,MainActivity::class.java)
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("USER_INFO")
        editor.apply()
        startActivity(intent)

        finish()
    }


}