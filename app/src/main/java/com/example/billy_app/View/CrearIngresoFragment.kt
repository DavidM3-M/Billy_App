package com.example.billy_app.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.billy_app.Model.entities.Ingreso // Cambio de entidad
import com.example.billy_app.R
import com.example.billy_app.ValidationUtils
import com.example.billy_app.ViewModel.IngresoViewModel // Cambio de ViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CrearIngresoFragment : Fragment() { // Cambio de nombre de la clase

    private val viewModel: IngresoViewModel by viewModels() // Cambio de ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_crear_ingreso, container, false) // Cambio de layout

        val btnVolver = root.findViewById<MaterialButton>(R.id.btnVolverInicio)
        val btnGuardar = root.findViewById<MaterialButton>(R.id.btnCrearIngreso) // Cambio de ID

        val edtMonto = root.findViewById<TextInputEditText>(R.id.ed_monto_ingreso) // Cambio de ID
        val edtFecha = root.findViewById<TextInputEditText>(R.id.ed_fecha_ingreso) // Cambio de ID
        val edtDescripcion = root.findViewById<TextInputEditText>(R.id.ed_descripcion_ingreso) // Cambio de ID

        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_crearIngresoFragment_to_inicioFragment) // Cambio en la navegación
        }

        btnGuardar.setOnClickListener {
            guardarIngreso(edtMonto, edtFecha, edtDescripcion)
        }

        return root
    }

    private fun guardarIngreso(edtMonto: TextInputEditText, edtFecha: TextInputEditText, edtDescripcion: TextInputEditText) {
        val monto = edtMonto.text.toString().toDoubleOrNull() ?: 0.0
        val fecha = edtFecha.text.toString()
        val descripcion = edtDescripcion.text.toString()

        val mensajeError = ValidationUtils.validarCampos(monto, fecha, descripcion)

        if (mensajeError != null) {
            Toast.makeText(requireContext(), mensajeError, Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoIngreso = Ingreso(monto = monto, fecha = fecha, descripcion = descripcion) // Cambio de entidad
        viewModel.guardarIngreso(nuevoIngreso) // Cambio de ViewModel
        Toast.makeText(requireContext(), "Ingreso guardado", Toast.LENGTH_SHORT).show()
    }
}