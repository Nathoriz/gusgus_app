package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.databinding.ItemCardMantenimientoBinding
import mood.honeyprojects.gusgusapp.model.entity.RecetaInsumo

class MantRecetaIsumoAdapter ( private val recetasinsumos:List<RecetaInsumo>, private val itemClickListener: MantRecetaIsumoAdapter.OnClickRecetaInsumoListener): RecyclerView.Adapter<MantRecetaIsumoAdapter.ViewHolder>()  {
    interface OnClickRecetaInsumoListener{
        fun onRecetaInsumoClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_mantenimiento,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: RecetaInsumo = recetasinsumos[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return recetasinsumos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardMantenimientoBinding.bind(itemView)

        fun bind(recetasinsumos: RecetaInsumo, position: Int){
            binding.tvNombreMantenimietno.text = recetasinsumos.insumo?.nombre
            binding.tvDetalleMantenimiento.text = recetasinsumos.cantidadUso.toString()
            binding.root.setOnClickListener{ recetasinsumos.id?.let { it1 ->
                itemClickListener.onRecetaInsumoClick(
                    it1,position
                )
            } }
        }
    }
}