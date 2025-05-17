package com.example.billy_app

object ValidationUtils {
    fun validarCampos(monto: Double, fecha: String, descripcion: String): String? {
        if (monto <= 0) return "El monto debe ser mayor a 0"
        if (fecha.isEmpty()) return "Ingrese una fecha válida"
        if (descripcion.isEmpty()) return "Ingrese una descripción"
        return null // Retorna null si todo es válido
    }
}
