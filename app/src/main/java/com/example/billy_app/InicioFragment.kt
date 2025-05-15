package com.example.billy_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton


class InicioFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_inicio, container, false)

        val btnIngresos = root.findViewById<MaterialButton>(R.id.btnIngresos)
        val btnGastos = root.findViewById<MaterialButton>(R.id.btnGastos)

        btnIngresos.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_ingresosFragment)
        }
        btnGastos.setOnClickListener {
            findNavController().navigate(R.id.action_inicioFragment_to_gastosFragment)
        }

        return root
    }
}