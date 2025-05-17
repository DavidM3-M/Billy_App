package com.example.billy_app.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.billy_app.Model.db.BillyDataBase
import com.example.billy_app.Model.entities.Ingreso
import kotlinx.coroutines.launch

class IngresoViewModel(application: Application) : AndroidViewModel(application) {
    private val db = BillyDataBase.getDatabase(application)
    private val dao = db.ingresoDao()

    fun guardarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            dao.insertIngreso(ingreso)
        }
    }
    fun actualizarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            dao.updateIngreso(ingreso)
        }
    }


    fun eliminarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            dao.deleteIngreso(ingreso)
        }
    }

}