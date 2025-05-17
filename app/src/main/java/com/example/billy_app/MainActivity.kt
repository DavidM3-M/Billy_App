package com.example.billy_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.billy_app.Model.db.BillyDataBase

class MainActivity : AppCompatActivity() {
    lateinit var base: BillyDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        base = BillyDataBase.getDatabase(applicationContext)

    }
}