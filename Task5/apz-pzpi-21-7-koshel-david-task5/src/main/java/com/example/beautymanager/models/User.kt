package com.example.beautymanager.models

class User(var email: String, var name : String, var surname : String, var patronymic: String, var phone : String, val salary : Int, val allowance :Int, val schedule : String, var saloonId: Long, val password :String, val roles : String, val id: Long? = null) {

}