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
import com.example.billy_app.ViewModel.GastoViewModel
import com.example.billy_app.adapter.GastoAdapter
import com.google.android.material.button.MaterialButton


class GastosFragment : Fragment() {

    private val viewModel: GastoViewModel by viewModels() // ✅ Se usa un solo ViewModel
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



        // ✅ Inicializar `GastoAdapter` correctamente PASANDO el ViewModel
        gastoAdapter = GastoAdapter(mutableListOf(), viewModel)
        recyclerGastos.adapter = gastoAdapter

        // ✅ Observar cambios en los datos y actualizar el adaptador
        viewModel.gastos.observe(viewLifecycleOwner) { nuevosGastos ->
            val scrollPos = (recyclerGastos.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val gastosOrdenados = nuevosGastos.reversed()
            val total = nuevosGastos.sumOf { it.monto } // 🔥 Suma los gastos
            val txtTotalGastos = view?.findViewById<TextView>(R.id.txtTotalGastos)

            gastoAdapter.actualizarLista(gastosOrdenados.toMutableList())
            txtTotalGastos?.text = "$$total"

            recyclerGastos.scrollToPosition(scrollPos) // 🔥 Restaura la posición del scroll
        }

        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_gastosFragment_to_inicioFragment)
        }

        btnAgregarGasto.setOnClickListener {
            findNavController().navigate(R.id.action_gastosFragment_to_crearGastoFragment)
        }

        return root
    }
}