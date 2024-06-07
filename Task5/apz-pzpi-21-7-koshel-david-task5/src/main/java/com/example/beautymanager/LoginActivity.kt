package com.example.beautymanager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.beautymanager.adapters.OrdersAdapter
import com.example.beautymanager.databinding.ActivityLoginBinding
import com.example.beautymanager.models.AuthRequest
import com.example.beautymanager.models.User
import com.google.gson.Gson
import com.google.gson.JsonNull
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        }

    fun onClickLogin(view: View){
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        if(email != "" && password != ""){
            val authRequest = AuthRequest(email,password)
            val client = OkHttpClient()
            val json = Gson().toJson(authRequest)
            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
            val tokenRequest = Request.Builder()
                .url("http://10.0.2.2:8091/auth/token")
                .post(requestBody)
                .build()
            var token = ""

            //intent.putExtra("email",email)

            client.newCall(tokenRequest).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    Log.d("LOGIN", "ERROR: ${e.message}")
                }
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    if (response.isSuccessful) {
                        runOnUiThread {
                            if (responseBody != null) {
                                token = responseBody
                                Log.d("LOGIN", "TOKEN: ${responseBody}")
                                val validateRequest = Request.Builder()
                                    .url("http://10.0.2.2:8091/auth/validate?token=$token")
                                    .get()
                                    .build()

                                client.newCall(validateRequest).enqueue(object : Callback {
                                    override fun onFailure(call: Call, e: IOException) {
                                        e.printStackTrace()
                                        Log.d("LOGIN", "ERROR: ${e.message}")
                                        runOnUiThread {
                                            Toast.makeText(this@LoginActivity, "Помилка", Toast.LENGTH_LONG).show()
                                        }
                                    }

                                    override fun onResponse(call: Call, response: Response) {
                                        val validateResponseBody = response.body?.string()
                                        Log.d("LOGIN", "VALIDATE RESPONSE: $validateResponseBody")
                                        runOnUiThread {
                                            if (response.isSuccessful && validateResponseBody == "Token is valid") {
                                                Toast.makeText(this@LoginActivity, "Успішна авторизація", Toast.LENGTH_LONG).show()
                                                fetchUser(email)
                                                Log.d("LOGIN","EMAIL $email")

                                                
                                            } else {
                                                Toast.makeText(this@LoginActivity, "Помилка токена", Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                })
                            }
                            else
                                Toast.makeText(this@LoginActivity, "Помилка", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@LoginActivity, "Помилка при авторизації", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }

    }

    fun onClickGoSignup(view: View){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)

    }

    private fun saveUser(user : User){
        val json = Gson().toJson(user)
        Log.d("LOGIN", "USER JSON: ${json}")
        sharedPreferences = getSharedPreferences("sharedPreferences",MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_INFO",json)
        editor.apply()

        val intent =  Intent(this,OrdersActivity::class.java)
        startActivity(intent)

        finish()
    }

    private fun fetchUser(email: String){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://10.0.2.2:8082/users/email/$email")
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //Log.d("ORDERS", "ERROR: ${e.message}")
                runOnUiThread {
                    //Toast.makeText(this@OrdersActivity, "Ошибка загрузки пользователя", Toast.LENGTH_LONG).show()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    val user = Gson().fromJson(responseBody, User::class.java)
                    runOnUiThread {
                        Log.d("LOGIN", "USER: ${user.id}")
                        saveUser(user)
                    }
                } else {
                    runOnUiThread {
                        //Toast.makeText(this@OrdersActivity, "Ошибка получения данных пользователя", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
