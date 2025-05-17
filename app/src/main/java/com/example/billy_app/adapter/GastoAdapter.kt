package com.example.billy_app.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.billy_app.EditarGastoDialog
import com.example.billy_app.Model.entities.Gasto
import com.example.billy_app.R
import com.google.android.material.button.MaterialButton

class GastoAdapter(private var gastos: MutableList<Gasto>) : RecyclerView.Adapter<GastoAdapter.ViewHolder>() {

    private var itemSeleccionado: Int = -1 // Índice del ítem expandido (-1 = ninguno)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMonto: TextView = itemView.findViewById(R.id.txtMontoGasto)
        val txtFecha: TextView = itemView.findViewById(R.id.txtFechaGasto)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcionGasto)
        val btnEditar: MaterialButton = itemView.findViewById(R.id.btnEditarGasto)
        val btnEliminar: MaterialButton = itemView.findViewById(R.id.btnEliminarGasto)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gasto, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gasto = gastos[position]
        holder.txtMonto.text = gasto.monto.toString()
        holder.txtFecha.text = gasto.fecha
        holder.txtDescripcion.text = gasto.descripcion

        // Controlar visibilidad de los botones sin afectar animaciones
        holder.btnEditar.visibility = if (position == itemSeleccionado) View.VISIBLE else View.GONE
        holder.btnEliminar.visibility = if (position == itemSeleccionado) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val posicionAnterior = itemSeleccionado
            itemSeleccionado = if (itemSeleccionado == position) -1 else position

            if (posicionAnterior != -1) notifyItemChanged(posicionAnterior)
            notifyItemChanged(position)
        }

        // Configurar botón de eliminar
        holder.btnEliminar.setOnClickListener {
            gastos.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, gastos.size) // Evita errores de índice al eliminar
        }

        // Configurar botón de editar sin modificar la animación
        holder.btnEditar.setOnClickListener {
            val dialog = EditarGastoDialog(gasto) { nuevoGasto ->
                gastos[position] = nuevoGasto
                notifyItemChanged(position)
            }
            dialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, "EditarGastoDialog")
        }
    }



    private fun animarVisibilidad(view: View, mostrar: Boolean) {
        val alphaFinal = if (mostrar) 1f else 0f
        val translationYFinal = if (mostrar) 0f else -20f // Efecto deslizante
        val duracion = 300L // Hacemos la animación más rápida para evitar saltos

        view.animate().apply {
            alpha(alphaFinal)
            translationY(translationYFinal)
            duration = duracion
            interpolator = DecelerateInterpolator()
            withEndAction { view.visibility = if (mostrar) View.VISIBLE else View.GONE }
            start()
        }
    }


    override fun getItemCount(): Int = gastos.size


    fun actualizarLista(nuevosGastos: List<Gasto>) {
        gastos.clear()
        gastos.addAll(nuevosGastos)
        notifyDataSetChanged() // Refresca la lista
    }
}