package com.example.beautymanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beautymanager.databinding.ActivityLoginBinding
import com.example.beautymanager.databinding.ActivitySignupBinding
import com.example.beautymanager.models.User
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class SignupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickGoOrdersPage(view: View){
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
    }

    fun onClickRegister(view: View){
        val email = binding.etEmail.text.toString()
        val name = binding.etName.text.toString()
        val lastname = binding.etLastname.text.toString()
        val patronymic = binding.edPatronymic.text.toString()
        val phone = binding.edPhone.text.toString()
        val inviteCode = binding.edCode.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (email != "" && name != "" && lastname != "" && patronymic != "" && phone != "" && inviteCode != "" && password != "" && confirmPassword != ""){
            if(password == confirmPassword){
                val user = User(email, name, lastname, patronymic,phone,0,0,"",0L,password,"ROLE_USER")

                val client = OkHttpClient()
                val gson = Gson()
                val json = gson.toJson(user)
                Log.d("REGISTER", json)
                val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), json)
                val request = Request.Builder()
                    .url("http://10.0.2.2:8091/auth/register?inviteCode=${inviteCode}")
                    .post(requestBody)
                    .build()
                Log.d("REGISTER", "START")
                client.newCall(request).enqueue(object : Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                        Log.d("REGISTER", "ERROR: ${e.message}")
                    }
                    override fun onResponse(call: Call, response: Response) {
                        val responseBody = response.body?.string()
                        user.saloonId=1
                        if (response.isSuccessful) {
                            runOnUiThread {
                                if(responseBody?.toLongOrNull() != null){
                                    Log.d("REGISTER", "USER INFO: ${email}")
                                    user.saloonId = responseBody.toLong()
                                    Toast.makeText(this@SignupActivity, "Реєстрація успішна", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this@SignupActivity,MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else if(responseBody=="Невірний код запрошення"){
                                    Log.d("REGISTER", "ERROR: ${responseBody}")
                                    Toast.makeText(this@SignupActivity, "Невірний код запрошення", Toast.LENGTH_LONG).show()
                                }
                                else {
                                    Log.d("REGISTER", "ERROR: ${responseBody}")
                                    Toast.makeText(this@SignupActivity, "Помилка", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            runOnUiThread {
                                Log.d("REGISTER", "SERVER ERROR: ${responseBody}")
                                Toast.makeText(this@SignupActivity, "Помилка при реєстрації", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
            }
            else
                Toast.makeText(this, "Паролі не співпадають", Toast.LENGTH_LONG).show()
        }
        else
            Toast.makeText(this, "Заповніть усі поля", Toast.LENGTH_LONG).show()
        Log.d("REGISTER", "END")
    }
}