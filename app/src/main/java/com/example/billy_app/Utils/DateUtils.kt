package com.example.billy_app.Utils

import android.app.DatePickerDialog
import android.content.Context
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class DateUtils {
    object DateUtils {
        fun mostrarDatePicker(context: Context, edtFecha: TextInputEditText) {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(context, { _, año, mes, día ->
                val fechaSeleccionada = "$día/${mes + 1}/$año"
                edtFecha.setText(fechaSeleccionada)
            }, year, month, day).show()
        }
    }
}