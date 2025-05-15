package com.example.billy_app.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingreso")
data class Ingreso(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monto: Double,
    val fecha: String,
    val descripcion: String
)
