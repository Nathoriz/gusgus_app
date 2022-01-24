package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Proveedor

class MantProveedorAdapter(private val proveedores:List<Proveedor>, private val itemClickListener: MantProveedorAdapter.OnClickProveedorListener): RecyclerView.Adapter<MantProveedorAdapter.ViewHolder>() {
    interface OnClickProveedorListener{
        fun onProveedorClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Proveedor = proveedores[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return proveedores.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(proveedor: Proveedor, position: Int){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = proveedor.nombre
            binding.root.setOnClickListener{ proveedor.id?.let { it1 ->
                itemClickListener.onProveedorClick(
                    it1,position
                )
            } }
        }
    }

}