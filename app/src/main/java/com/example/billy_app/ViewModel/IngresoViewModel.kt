package com.example.billy_app.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.billy_app.Model.db.BillyDataBase
import com.example.billy_app.Model.entities.Ingreso
import kotlinx.coroutines.launch

class IngresoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = BillyDataBase.getDatabase(application)
    private val dao = db.ingresoDao() // Cambio a la DAO de ingresos
    val ingresos: LiveData<List<Ingreso>> = dao.getAllIngresos() // Cambio de entidad

    fun guardarIngreso(ingreso: Ingreso) {
        viewModelScope.launch { dao.insertIngreso(ingreso) } // Adaptación del método
    }

    fun eliminarIngreso(ingreso: Ingreso) {
        viewModelScope.launch { dao.deleteIngreso(ingreso) } // Adaptación del método
    }

    fun actualizarIngreso(ingreso: Ingreso) {
        viewModelScope.launch { dao.updateIngreso(ingreso) } // Adaptación del método
    }

    fun obtenerIngresos(): LiveData<List<Ingreso>> = dao.getAllIngresos() // Cambio de entidad
}