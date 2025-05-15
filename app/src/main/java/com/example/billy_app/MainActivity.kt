package com.example.billy_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.billy_app.room.db.BillyDataBase
import com.example.billy_app.room.entities.Ingreso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var base: BillyDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        base = BillyDataBase.getDatabase(applicationContext)

        GlobalScope.launch {
            base.ingresoDao().insertIngreso(Ingreso(
                monto = 1.200,
                fecha = "20 de Abril",
                descripcion = "Pago de Trabajo" ))
        }
    }
}