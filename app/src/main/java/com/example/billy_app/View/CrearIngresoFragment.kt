package com.example.billy_app.View

import android.app.DatePickerDialog
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
import java.util.Calendar

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

        edtFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, año, mes, día ->
                val fechaSeleccionada = "$día/${mes + 1}/$año" // ✅ Convierte la fecha a texto
                edtFecha.setText(fechaSeleccionada) // ✅ Establece la fecha en el campo de texto
            }, year, month, day).show()
        }

        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_crearIngresoFragment_to_inicioFragment) // Cambio en la navegación
        }

        btnGuardar.setOnClickListener {
            guardarIngreso(edtMonto, edtFecha, edtDescripcion)
        }

        return root
    }

    private fun guardarIngreso(edtMonto: TextInputEditText, edtFecha: TextInputEditText, edtDescripcion: TextInputEditText) {
        val montoStr = edtMonto.text.toString().trim()

        // ✅ Validación mejorada para evitar textos no numéricos
        if (montoStr.isBlank() || montoStr.toDoubleOrNull() == null) {
            Toast.makeText(requireContext(), "⚠️ El monto debe ser un número válido", Toast.LENGTH_SHORT).show()
            return
        }

        val monto = montoStr.toDouble()
        val fecha = edtFecha.text.toString().trim()
        val descripcion = edtDescripcion.text.toString().trim()

        // ✅ Validar con `ValidationUtils`
        val mensajeError = ValidationUtils.validarCampos(montoStr, fecha, descripcion, requireContext())

        if (mensajeError != null) {
            Toast.makeText(requireContext(), mensajeError, Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoIngreso = Ingreso(monto = monto, fecha = fecha, descripcion = descripcion)
        viewModel.guardarIngreso(nuevoIngreso)
        Toast.makeText(requireContext(), "✅ Ingreso guardado con éxito", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_crearIngresoFragment_to_inicioFragment)
    }
}