package com.example.billy_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.billy_app.Dialogs.EditarGastoDialog
import com.example.billy_app.Model.entities.Gasto
import com.example.billy_app.R
import com.example.billy_app.ViewModel.GastoViewModel
import com.google.android.material.button.MaterialButton

class GastoAdapter(
    private var gastos: MutableList<Gasto>,
    private val viewModel: GastoViewModel // ✅ Se pasa el ViewModel
) : RecyclerView.Adapter<GastoAdapter.ViewHolder>() {

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
        holder.btnEliminar.visibility =
            if (position == itemSeleccionado) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val posicionAnterior = itemSeleccionado
            itemSeleccionado = if (itemSeleccionado == position) -1 else position

            if (posicionAnterior != -1) notifyItemChanged(posicionAnterior)
            notifyItemChanged(position)
        }

        // ✅ Configurar botón de eliminar correctamente
        holder.btnEliminar.setOnClickListener {
            viewModel.eliminarGasto(gasto) // 🗑 Elimina de la base de datos
            gastos.removeAt(position) // Elimina de la lista visual
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, gastos.size)
        }

        // Configurar botón de editar sin modificar la animación
        holder.btnEditar.setOnClickListener {
            val dialog = EditarGastoDialog(gasto) { nuevoGasto ->
                viewModel.actualizarGasto(nuevoGasto) // ✅ Llamamos la función correcta
                gastos[position] = nuevoGasto // ✅ Se actualiza en la lista de la UI
                notifyItemChanged(position) // ✅ Refresca el `RecyclerView`
            }
            dialog.show(
                (holder.itemView.context as AppCompatActivity).supportFragmentManager,
                "EditarGastoDialog"
            )
        }
    }

    override fun getItemCount(): Int = gastos.size

    fun actualizarLista(nuevosGastos: List<Gasto>) {
        gastos.clear()
        gastos.addAll(nuevosGastos)
        notifyDataSetChanged() // Refresca la lista
    }
}