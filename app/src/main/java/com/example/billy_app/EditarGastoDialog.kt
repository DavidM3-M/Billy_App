package com.example.billy_app

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.compose.material3.DatePickerDialog
import androidx.fragment.app.DialogFragment
import com.example.billy_app.Model.entities.Gasto
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class EditarGastoDialog(private val gasto: Gasto, private val listener: (Gasto) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_editar_gasto, null)

        val inputMonto = view.findViewById<TextInputEditText>(R.id.inputMonto)
        val inputDescripcion = view.findViewById<TextInputEditText>(R.id.inputDescripcion)
        val inputFecha = view.findViewById<TextInputEditText>(R.id.inputFecha) // 🔥 Agregamos la fecha
        val btnGuardar = view.findViewById<MaterialButton>(R.id.btnGuardar)
        val btnCancelar = view.findViewById<MaterialButton>(R.id.btnCancelar)

        inputMonto.setText(gasto.monto.toString())
        inputDescripcion.setText(gasto.descripcion)
        inputFecha.setText(gasto.fecha) // Mostramos la fecha actual

        // 🔥 Manejo de selección de fecha con `DatePickerDialog`
        inputFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, año, mes, día ->
                val fechaSeleccionada = "$día/${mes + 1}/$año" // Formato simple
                inputFecha.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }

        // 🔥 Guardar cambios incluyendo la fecha seleccionada
        btnGuardar.setOnClickListener {
            val montoEditado = inputMonto.text.toString().toDoubleOrNull()
            val descripcionEditada = inputDescripcion.text.toString()
            val fechaEditada = inputFecha.text.toString()

            if (montoEditado == null || montoEditado < 0) {
                inputMonto.error = "Ingrese un monto válido"
            } else if (descripcionEditada.isEmpty()) {
                inputDescripcion.error = "Ingrese una descripción"
            } else if (fechaEditada.isEmpty()) {
                inputFecha.error = "Seleccione una fecha"
            } else {
                val nuevoGasto = gasto.copy(monto = montoEditado, descripcion = descripcionEditada, fecha = fechaEditada)
                listener(nuevoGasto)
                dialog?.dismiss()
            }
        }

        btnCancelar.setOnClickListener { dialog?.dismiss() }

        return builder.setView(view).create()
    }
}