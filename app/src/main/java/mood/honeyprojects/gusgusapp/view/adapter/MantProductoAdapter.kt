package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto

class MantProductoAdapter(private val productos:List<Producto>, private val itemClickListener: MantProductoAdapter.OnClickProductoListener): RecyclerView.Adapter<MantProductoAdapter.ViewHolder>() {
    interface OnClickProductoListener{
        fun onProductoClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Producto = productos[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(producto: Producto, position: Int){
            Picasso.get().load( producto.urlimg ).into( binding.ivImageDesign )
            binding.tvTextDesign.text = producto.nombre
            binding.root.setOnClickListener{ producto.id?.let { it1 ->
                itemClickListener.onProductoClick(
                    it1,position
                )
            } }
        }
    }
}