package com.example.beautymanager.models

import java.io.Serializable
import java.net.IDN
import java.sql.Timestamp

class Order(val name: String, val datetime: String, val totalCost: Int, val ownerId: Long, val saloonId: Long, val staffId: Long,
            var status: String, val id: Long? = null) : Serializable {
}