package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDiametroBinding
import mood.honeyprojects.gusgusapp.model.entity.ProductoDiametro

class ProductoDiametroAdapter(private val diametros:List<ProductoDiametro>) : RecyclerView.Adapter<ProductoDiametroAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_diametro,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProductoDiametro = diametros[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return diametros.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDiametroBinding.bind(itemView)

        fun bind(diametro: ProductoDiametro, position: Int){
            binding.tvNombreItemdiametro.text = diametro.diametro?.descripcion.toString()
        }
    }
}