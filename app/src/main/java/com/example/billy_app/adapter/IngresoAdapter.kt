package com.example.billy_app.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.billy_app.Dialogs.EditarIngresoDialog // Ajustar si es necesario
import com.example.billy_app.Model.entities.Ingreso // Cambio de entidad
import com.example.billy_app.R
import com.example.billy_app.ViewModel.IngresoViewModel
import com.google.android.material.button.MaterialButton

class IngresoAdapter(
    private var ingresos: MutableList<Ingreso>,
    private val viewModel: IngresoViewModel // ✅ Se pasa el ViewModel
) : RecyclerView.Adapter<IngresoAdapter.ViewHolder>() {

    private var itemSeleccionado: Int = -1 // Índice del ítem expandido (-1 = ninguno)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMonto: TextView = itemView.findViewById(R.id.txtMontoIngreso)
        val txtFecha: TextView = itemView.findViewById(R.id.txtFechaIngreso)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcionIngreso)
        val btnEditar: MaterialButton = itemView.findViewById(R.id.btnEditarIngreso)
        val btnEliminar: MaterialButton = itemView.findViewById(R.id.btnEliminarIngreso)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingreso, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingreso = ingresos[position]
        holder.txtMonto.text = ingreso.monto.toString()
        holder.txtFecha.text = ingreso.fecha
        holder.txtDescripcion.text = ingreso.descripcion

        holder.btnEditar.visibility = if (position == itemSeleccionado) View.VISIBLE else View.GONE
        holder.btnEliminar.visibility = if (position == itemSeleccionado) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val posicionAnterior = itemSeleccionado
            itemSeleccionado = if (itemSeleccionado == position) -1 else position

            if (posicionAnterior != -1) notifyItemChanged(posicionAnterior)
            notifyItemChanged(position)
        }

        holder.btnEliminar.setOnClickListener {
            viewModel.eliminarIngreso(ingreso) // 🗑 Elimina de la base de datos
            ingresos.removeAt(position) // Elimina de la lista visual
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, ingresos.size)
        }

        holder.btnEditar.setOnClickListener {
            val dialog = EditarIngresoDialog(ingreso) { nuevoIngreso ->
                viewModel.actualizarIngreso(nuevoIngreso) // ✅ Llamamos la función correcta
                ingresos[position] = nuevoIngreso // ✅ Se actualiza en la lista de la UI
                notifyItemChanged(position) // ✅ Refresca el `RecyclerView`
            }
            dialog.show(
                (holder.itemView.context as AppCompatActivity).supportFragmentManager,
                "EditarIngresoDialog"
            )
        }
    }

    override fun getItemCount(): Int = ingresos.size

    fun actualizarLista(nuevosIngresos: List<Ingreso>) {
        ingresos.clear()
        ingresos.addAll(nuevosIngresos)
        notifyDataSetChanged() // Refresca la lista
    }
}