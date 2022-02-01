package mood.honeyprojects.gusgusapp.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardSaborBinding
import mood.honeyprojects.gusgusapp.model.entity.ProductoSabor

class ProductoSaborAdapter(private val sabores:List<ProductoSabor>) : RecyclerView.Adapter<ProductoSaborAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_sabor,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProductoSabor = sabores[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return sabores.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardSaborBinding.bind(itemView)

        fun bind(saborp: ProductoSabor, position: Int){
            val color = "#" + saborp.sabor?.color.toString()
            val colorSabor = Color.parseColor(color)
            binding.ivColorItemsabor.setColorFilter(colorSabor)
            binding.tvNombreItemsabor.text = saborp.sabor?.nombre.toString()
        }
    }
}