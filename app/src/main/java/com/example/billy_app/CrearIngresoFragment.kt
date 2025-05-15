package com.example.billy_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton


class CrearIngresoFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_crear_ingreso, container, false)
        val btnVolver = root.findViewById<MaterialButton>(R.id.btnVolverInicio)
        //val btnAgregarIngreso = root.findViewById<MaterialButton>(R.id.btnAgregarIngreso)
        btnVolver.setOnClickListener {
            findNavController().navigate(R.id.action_crearIngresoFragment_to_inicioFragment)
        }

        return root
    }

}