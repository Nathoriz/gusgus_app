package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardRellenoBinding
import mood.honeyprojects.gusgusapp.model.entity.ProductoRelleno

class ProductoRellenoAdapter(private val rellenos:List<ProductoRelleno>) : RecyclerView.Adapter<ProductoRellenoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_relleno,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProductoRelleno = rellenos[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return rellenos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardRellenoBinding.bind(itemView)

        fun bind(relleno: ProductoRelleno, position: Int){
            binding.tvNombreItemrelleno.text = relleno.relleno?.descripcion.toString()
        }
    }
}