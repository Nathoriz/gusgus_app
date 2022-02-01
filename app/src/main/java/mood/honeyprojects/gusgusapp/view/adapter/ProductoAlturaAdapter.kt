package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardAlturaBinding
import mood.honeyprojects.gusgusapp.model.entity.ProductoAltura

class ProductoAlturaAdapter(private val alturas:List<ProductoAltura>) : RecyclerView.Adapter<ProductoAlturaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_altura,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProductoAltura = alturas[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return alturas.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardAlturaBinding.bind(itemView)

        fun bind(altura: ProductoAltura, position: Int){
            binding.tvNombreItemaltura.text = altura.altura?.descripcion.toString()
        }
    }
}