package com.example.billy_app.Utils

import android.content.Context
import android.widget.Toast

object ValidationUtils {
    fun validarCampos(montoStr: String, fecha: String, descripcion: String, context: Context): String? {
        val monto = montoStr.toDoubleOrNull()

        when {
            monto == null || monto <= 0 -> {
                mostrarToast(context, "⚠️ El monto debe ser un número mayor a 0")
                return "El monto debe ser un número válido y mayor a 0"
            }
            fecha.isBlank() -> {
                mostrarToast(context, "⚠️ Ingrese una fecha válida")
                return "Ingrese una fecha válida"
            }
            descripcion.isBlank() -> {
                mostrarToast(context, "⚠️ Ingrese una descripción")
                return "Ingrese una descripción"
            }
        }
        return null
    }

    private fun mostrarToast(context: Context, mensaje: String) {
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
    }
}
