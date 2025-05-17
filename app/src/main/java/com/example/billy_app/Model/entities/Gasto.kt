package com.example.billy_app.Model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gasto")
data class Gasto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monto: Double,
    val fecha: String,
    val descripcion: String
)
