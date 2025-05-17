package com.example.billy_app.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.billy_app.R
import com.google.android.material.button.MaterialButton


class IngresosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_ingresos, container, false)

        val btnVolver = root.findViewById<MaterialButton>(R.id.btnVolverInicio)
        val btnAgregarIngreso = root.findViewById<MaterialButton>(R.id.btnAgregarIngreso)
        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_ingresosFragment_to_inicioFragment)
        }
        btnAgregarIngreso.setOnClickListener {
            findNavController().navigate(R.id.action_ingresosFragment_to_crearIngresoFragment)
        }



        return root
    }
}