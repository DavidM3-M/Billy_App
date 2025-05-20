package com.example.billy_app.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.billy_app.Model.entities.Gasto
import com.example.billy_app.R
import com.example.billy_app.Utils.DateUtils.DateUtils.mostrarDatePicker
import com.example.billy_app.Utils.ValidationUtils.validarCampos
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

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

        // ✅ Manejo de selección de fecha con `mostrarDatePicker()`
        inputFecha.setOnClickListener {
            mostrarDatePicker(requireContext(), inputFecha)
        }

        // ✅ Validar antes de guardar cambios
        btnGuardar.setOnClickListener {
            val montoStr = inputMonto.text.toString().trim()
            val fechaEditada = inputFecha.text.toString().trim()
            val descripcionEditada = inputDescripcion.text.toString().trim()

            val mensajeError = validarCampos(montoStr, fechaEditada, descripcionEditada, requireContext())

            if (mensajeError != null) {
                return@setOnClickListener // 🔥 Evita guardar si hay errores
            }

            val montoEditado = montoStr.toDouble()
            val nuevoGasto = gasto.copy(monto = montoEditado, descripcion = descripcionEditada, fecha = fechaEditada)
            listener(nuevoGasto)
            dismiss() // ✅ Cerrar el diálogo al guardar
        }

        // ✅ Cancelar sin cambios
        btnCancelar.setOnClickListener { dismiss() }

        return builder.setView(view).create()
    }
}