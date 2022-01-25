package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardMantenimientoBinding
import mood.honeyprojects.gusgusapp.model.entity.ProveedorInsumo

class MantProveedorInsumoAdapter(private val insumoproveedores:List<ProveedorInsumo>, private val itemClickListener: MantProveedorInsumoAdapter.OnClickProveedorInsumoListener): RecyclerView.Adapter<MantProveedorInsumoAdapter.ViewHolder>() {
    interface OnClickProveedorInsumoListener{
        fun onProveedorInsumoClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_mantenimiento,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProveedorInsumo = insumoproveedores[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return insumoproveedores.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardMantenimientoBinding.bind(itemView)

        fun bind(proveedorinsumo: ProveedorInsumo, position: Int){
            binding.tvNombreMantenimietno.text = proveedorinsumo.insumo?.nombre
            binding.tvDetalleMantenimiento.text = proveedorinsumo.precio.toString()
            binding.root.setOnClickListener{ proveedorinsumo.id?.let { it1 ->
                itemClickListener.onProveedorInsumoClick(
                    it1,position
                )
            } }
        }
    }
}