package com.example.billy_app

import android.content.Context
import android.widget.Toast

object ValidationUtils {
    fun validarCampos(montoStr: String, fecha: String, descripcion: String, context: Context): String? {
        val monto = montoStr.toDoubleOrNull()

        if (monto == null || monto <= 0) {
            Toast.makeText(context, "⚠️ El monto debe ser un número mayor a 0", Toast.LENGTH_SHORT).show()
            return "El monto debe ser un número válido y mayor a 0"
        }
        if (fecha.isBlank()) return "Ingrese una fecha válida"
        if (descripcion.isBlank()) return "Ingrese una descripción"

        return null
    }
}
