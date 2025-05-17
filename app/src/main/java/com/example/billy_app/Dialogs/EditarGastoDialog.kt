package com.example.billy_app.Dialogs

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.billy_app.Model.entities.Gasto
import com.example.billy_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class EditarGastoDialog(
    private val gasto: Gasto,
    private val listener: (Gasto) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_editar_gasto, null)

        val inputMonto = view.findViewById<TextInputEditText>(R.id.inputMonto)
        val inputDescripcion = view.findViewById<TextInputEditText>(R.id.inputDescripcion)
        val inputFecha = view.findViewById<TextInputEditText>(R.id.inputFecha)
        val btnGuardar = view.findViewById<MaterialButton>(R.id.btnGuardar)
        val btnCancelar = view.findViewById<MaterialButton>(R.id.btnCancelar)

        // ✅ Prellenar datos actuales
        inputMonto.setText(gasto.monto.toString())
        inputDescripcion.setText(gasto.descripcion)
        inputFecha.setText(gasto.fecha)

        // ✅ Manejo de selección de fecha con `DatePickerDialog`
        inputFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, año, mes, día ->
                val fechaSeleccionada = "$día/${mes + 1}/$año"
                inputFecha.setText(fechaSeleccionada)
            }, year, month, day).show()
        }

        // ✅ Guardar cambios con validaciones mejoradas
        btnGuardar.setOnClickListener {
            val montoEditado = inputMonto.text.toString().toDoubleOrNull()
            val descripcionEditada = inputDescripcion.text.toString().trim()
            val fechaEditada = inputFecha.text.toString().trim()

            when {
                montoEditado == null || montoEditado < 0 -> inputMonto.error = "Ingrese un monto válido"
                descripcionEditada.isEmpty() -> inputDescripcion.error = "Ingrese una descripción"
                fechaEditada.isEmpty() -> inputFecha.error = "Seleccione una fecha"
                else -> {
                    val nuevoGasto = gasto.copy(monto = montoEditado, descripcion = descripcionEditada, fecha = fechaEditada)
                    listener(nuevoGasto)
                    dismiss() // ✅ Cerrar el diálogo al guardar
                }
            }
        }

        // ✅ Cancelar sin cambios
        btnCancelar.setOnClickListener { dismiss() }

        return builder.setView(view).create()
    }
}