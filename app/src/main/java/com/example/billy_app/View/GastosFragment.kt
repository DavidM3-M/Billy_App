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
import com.example.billy_app.ViewModel.GastoViewModel
import com.example.billy_app.adapter.GastoAdapter
import com.google.android.material.button.MaterialButton


class GastosFragment : Fragment() {
    private val viewModel: GastoViewModel by viewModels()
    private lateinit var gastosViewModel: GastoViewModel
    private lateinit var gastoAdapter: GastoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_gastos, container, false)

        val btnVolver = root.findViewById<MaterialButton>(R.id.btnVolverInicio)
        val btnAgregarGasto = root.findViewById<MaterialButton>(R.id.btnAgregarGasto)
        val recyclerGastos = root.findViewById<RecyclerView>(R.id.recyclerGastos)

        recyclerGastos.layoutManager = LinearLayoutManager(requireContext())

        // ✅ Inicializar ViewModel
        gastosViewModel = ViewModelProvider(requireActivity()).get(GastoViewModel::class.java)

        // ✅ Inicializar `GastoAdapter` correctamente con una lista vacía
        gastoAdapter = GastoAdapter(mutableListOf())
        recyclerGastos.adapter = gastoAdapter

        // ✅ Observar cambios en los datos y actualizar el adaptador
        gastosViewModel.gastos.observe(viewLifecycleOwner) { nuevosGastos ->
            gastoAdapter.actualizarLista(nuevosGastos.toMutableList()) // Sincroniza UI con datos reales
        }

        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_gastosFragment_to_inicioFragment)
        }

        btnAgregarGasto.setOnClickListener {
            findNavController().navigate(R.id.action_gastosFragment_to_crearGastoFragment)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.obtenerGastos().observe(viewLifecycleOwner) { gastos ->
            gastoAdapter.actualizarLista(gastos) // Ahora correctamente sobre el objeto
        }
    }
}