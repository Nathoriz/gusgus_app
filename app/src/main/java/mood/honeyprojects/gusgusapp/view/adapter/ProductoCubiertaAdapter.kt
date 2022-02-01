package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardCubiertaBinding
import mood.honeyprojects.gusgusapp.model.entity.ProductoCubierta

class ProductoCubiertaAdapter(private val cubiertas:List<ProductoCubierta>) : RecyclerView.Adapter<ProductoCubiertaAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_cubierta,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ProductoCubierta = cubiertas[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return cubiertas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardCubiertaBinding.bind(itemView)

        fun bind(cubierta: ProductoCubierta, position: Int){
            binding.tvNombreItemcubierta.text = cubierta.cubierta?.nombre.toString()
        }
    }
}