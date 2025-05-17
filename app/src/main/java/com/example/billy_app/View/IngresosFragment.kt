package com.example.billy_app.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billy_app.R
import com.example.billy_app.ViewModel.IngresoViewModel
import com.example.billy_app.adapter.IngresoAdapter
import com.google.android.material.button.MaterialButton


class IngresosFragment : Fragment() {

    private val viewModel: IngresoViewModel by viewModels() // ✅ Se usa un solo ViewModel
    private lateinit var ingresoAdapter: IngresoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_ingresos, container, false)

        val btnVolver = root.findViewById<MaterialButton>(R.id.btnVolverInicio)
        val btnAgregarIngreso = root.findViewById<MaterialButton>(R.id.btnAgregarIngreso)
        val recyclerIngresos = root.findViewById<RecyclerView>(R.id.recyclerIngresos)

        recyclerIngresos.layoutManager = LinearLayoutManager(requireContext())

        // ✅ Inicializar `IngresoAdapter` correctamente PASANDO el ViewModel
        ingresoAdapter = IngresoAdapter(mutableListOf(), viewModel)
        recyclerIngresos.adapter = ingresoAdapter

        // ✅ Observar cambios en los datos y actualizar el adaptador
        viewModel.ingresos.observe(viewLifecycleOwner) { nuevosIngresos ->
            ingresoAdapter.actualizarLista(nuevosIngresos.toMutableList()) // Sincroniza UI con datos reales
        }

        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_ingresosFragment_to_inicioFragment)
        }

        btnAgregarIngreso.setOnClickListener {
            findNavController().navigate(R.id.action_ingresosFragment_to_crearIngresoFragment)
        }

        return root
    }
}