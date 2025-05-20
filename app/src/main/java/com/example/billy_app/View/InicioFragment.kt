package com.example.billy_app.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.billy_app.R
import com.example.billy_app.ViewModel.GastoViewModel
import com.example.billy_app.ViewModel.IngresoViewModel
import com.google.android.material.button.MaterialButton
import androidx.activity.OnBackPressedCallback

class InicioFragment : Fragment() {

    private val ingresoViewModel: IngresoViewModel by viewModels()
    private val gastoViewModel: GastoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_inicio, container, false)

        val btnIngresos = root.findViewById<MaterialButton>(R.id.btnIngresos)
        val btnGastos = root.findViewById<MaterialButton>(R.id.btnGastos)
        val txtBalance = root.findViewById<TextView>(R.id.textViewTotal)

        btnIngresos.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_ingresosFragment)
        }

        btnGastos.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_gastosFragment)
        }

        // ✅ Observar ingresos y gastos para actualizar el balance dinámicamente
        ingresoViewModel.obtenerTotalIngresos().observe(viewLifecycleOwner) { totalIngresos ->
            gastoViewModel.obtenerTotalGastos().observe(viewLifecycleOwner) { totalGastos ->
                val balance = (totalIngresos ?: 0.0) - (totalGastos ?: 0.0)
                txtBalance.text = "$ $balance"
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔥 Implementación para cerrar la app cuando el usuario presione "Atrás"
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish() // 🔥 Cierra la app completamente
            }
        })
    }
}