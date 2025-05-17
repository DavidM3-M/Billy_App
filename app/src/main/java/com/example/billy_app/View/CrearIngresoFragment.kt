package com.example.billy_app.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.billy_app.R
import com.example.billy_app.ViewModel.IngresoViewModel
import com.example.billy_app.Model.entities.Ingreso
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CrearIngresoFragment : Fragment() {
    private val viewModel: IngresoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_crear_ingreso, container, false)

        val btnVolver = root.findViewById<MaterialButton>(R.id.btnVolverInicio)
        val btnGuardar = root.findViewById<MaterialButton>(R.id.btnCrearIngreso)

        val inputMonto = root.findViewById<TextInputLayout>(R.id.inputMonto)
        val edtMonto = root.findViewById<TextInputEditText>(R.id.ed_monto_ingreso)

        val inputFecha = root.findViewById<TextInputLayout>(R.id.inputFecha)
        val edtFecha = root.findViewById<TextInputEditText>(R.id.ed_fecha_ingreso)

        val inputDescripcion = root.findViewById<TextInputLayout>(R.id.inputDescripcion)
        val edtDescripcion = root.findViewById<TextInputEditText>(R.id.ed_descripcion_ingreso)

        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_crearIngresoFragment_to_inicioFragment)
        }

        btnGuardar.setOnClickListener {
            guardarIngreso(edtMonto, edtFecha, edtDescripcion, inputMonto, inputFecha, inputDescripcion)
        }

        return root
    }

    private fun guardarIngreso(
        edtMonto: TextInputEditText, edtFecha: TextInputEditText, edtDescripcion: TextInputEditText,
        inputMonto: TextInputLayout, inputFecha: TextInputLayout, inputDescripcion: TextInputLayout
    ) {
        val monto = edtMonto.text.toString().toDoubleOrNull() ?: 0.0
        val fecha = edtFecha.text.toString()
        val descripcion = edtDescripcion.text.toString()

        if (validarCampos(monto, fecha, descripcion, inputMonto, inputFecha, inputDescripcion)) {
            val nuevoIngreso = Ingreso(monto = monto, fecha = fecha, descripcion = descripcion)
            viewModel.guardarIngreso(nuevoIngreso)
            Toast.makeText(requireContext(), "Ingreso guardado", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_crearIngresoFragment_to_inicioFragment)
        }
    }

    private fun validarCampos(
        monto: Double, fecha: String, descripcion: String,
        inputMonto: TextInputLayout, inputFecha: TextInputLayout, inputDescripcion: TextInputLayout
    ): Boolean {
        var valido = true

        if (monto <= 0) {
            inputMonto.error = "El monto debe ser mayor a 0"
            valido = false
        } else {
            inputMonto.error = null
        }

        if (fecha.isEmpty()) {
            inputFecha.error = "Ingrese una fecha válida"
            valido = false
        } else {
            inputFecha.error = null
        }

        if (descripcion.isEmpty()) {
            inputDescripcion.error = "Ingrese una descripción"
            valido = false
        } else {
            inputDescripcion.error = null
        }

        return valido
    }
}