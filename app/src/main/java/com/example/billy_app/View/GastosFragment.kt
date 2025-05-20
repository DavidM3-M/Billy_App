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
import com.example.billy_app.ViewModel.GastoViewModel
import com.example.billy_app.adapter.GastoAdapter
import com.google.android.material.button.MaterialButton
import androidx.activity.OnBackPressedCallback

class GastosFragment : Fragment() {

    private val viewModel: GastoViewModel by viewModels()
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

        gastoAdapter = GastoAdapter(mutableListOf(), viewModel)
        recyclerGastos.adapter = gastoAdapter

        viewModel.gastos.observe(viewLifecycleOwner) { nuevosGastos ->
            val scrollPos = (recyclerGastos.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val gastosOrdenados = nuevosGastos.reversed()
            val total = nuevosGastos.sumOf { it.monto }
            val txtTotalGastos = view?.findViewById<TextView>(R.id.txtTotalGastos)

            gastoAdapter.actualizarLista(gastosOrdenados.toMutableList())
            txtTotalGastos?.text = "$$total"

            recyclerGastos.scrollToPosition(scrollPos)
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

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_gastosFragment_to_inicioFragment) // 🔥 Vuelve al inicio cuando el usuario presione "Atrás"
            }
        })
    }
}