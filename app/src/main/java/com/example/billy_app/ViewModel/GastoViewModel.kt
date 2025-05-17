package com.example.billy_app.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.billy_app.Model.db.BillyDataBase
import com.example.billy_app.Model.entities.Gasto
import kotlinx.coroutines.launch

class GastoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = BillyDataBase.getDatabase(application)
    private val dao = db.gastoDao()
    val gastos: LiveData<List<Gasto>> = dao.getAllGastos()

    fun guardarGasto(gasto: Gasto) {
        viewModelScope.launch { dao.insertGasto(gasto) }
    }

    fun eliminarGasto(gasto: Gasto) {
        viewModelScope.launch { dao.deleteGasto(gasto) }
    }

    fun actualizarGasto(gasto: Gasto) {
        viewModelScope.launch { dao.updateGasto(gasto) }
    }


    fun obtenerTotalGastos(): LiveData<Double> {
        return dao.getTotalGastos()
    }

}