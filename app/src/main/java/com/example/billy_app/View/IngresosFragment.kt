package com.example.billy_app.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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


        ingresoAdapter = IngresoAdapter(mutableListOf(), viewModel)
        recyclerIngresos.adapter = ingresoAdapter

        // ✅ Observar cambios en los datos y actualizar el adaptador
        viewModel.ingresos.observe(viewLifecycleOwner) { nuevosIngresos ->
            val scrollPos = (recyclerIngresos.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val total = nuevosIngresos.sumOf { it.monto } // 🔥 Suma los ingresos
            val txtTotalIngresos = view?.findViewById<TextView>(R.id.txtTotalIngresos)

            val ingresosInvertidos = nuevosIngresos.reversed() // 🔥 Coloca el último ingreso primero
            ingresoAdapter.actualizarLista(ingresosInvertidos.toMutableList()) // Sincroniza UI con datos reales
            txtTotalIngresos?.text = "$$total"


            recyclerIngresos.scrollToPosition(scrollPos)
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