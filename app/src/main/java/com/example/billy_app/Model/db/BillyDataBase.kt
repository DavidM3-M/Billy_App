package com.example.billy_app.Model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.billy_app.Model.daos.GastoDao
import com.example.billy_app.Model.daos.IngresoDao
import com.example.billy_app.Model.entities.Gasto
import com.example.billy_app.Model.entities.Ingreso

@Database(entities = [Ingreso::class, Gasto::class], version = 1)

abstract class BillyDataBase:RoomDatabase(){
    abstract fun ingresoDao(): IngresoDao
    abstract fun gastoDao(): GastoDao

    companion object{
        fun getDatabase(ctx: Context): BillyDataBase {
            val db = Room.databaseBuilder(ctx, BillyDataBase::class.java, "billy_db").build()
            return db
        }
    }
}