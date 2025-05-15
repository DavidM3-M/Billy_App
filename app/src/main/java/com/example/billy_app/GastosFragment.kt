package com.example.billy_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton


class GastosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_gastos, container, false)

        val btnVolver = root.findViewById<MaterialButton>(R.id.btnVolverInicio)
        val btnAgregarGasto = root.findViewById<MaterialButton>(R.id.btnAgregarGasto)
        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_gastosFragment_to_inicioFragment)
        }
        btnAgregarGasto.setOnClickListener {
            findNavController().navigate(R.id.action_gastosFragment_to_crearGastoFragment)
        }

        return root
    }
}