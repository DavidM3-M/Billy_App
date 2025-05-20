package com.example.billy_app.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billy_app.R
import com.example.billy_app.ViewModel.IngresoViewModel
import com.example.billy_app.adapter.IngresoAdapter
import com.google.android.material.button.MaterialButton
import androidx.activity.OnBackPressedCallback

class IngresosFragment : Fragment() {

    private val viewModel: IngresoViewModel by viewModels()
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

        viewModel.ingresos.observe(viewLifecycleOwner) { nuevosIngresos ->
            val scrollPos = (recyclerIngresos.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val total = nuevosIngresos.sumOf { it.monto }
            val txtTotalIngresos = view?.findViewById<TextView>(R.id.txtTotalIngresos)

            val ingresosInvertidos = nuevosIngresos.reversed()
            ingresoAdapter.actualizarLista(ingresosInvertidos.toMutableList())
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_ingresosFragment_to_inicioFragment) // 🔥 Vuelve al inicio
            }
        })
    }
}